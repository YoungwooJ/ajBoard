package com.example.demo.service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardFileDTO;
import com.example.demo.domain.BoardFormDTO;
import com.example.demo.repository.BoardFileMapper;
import com.example.demo.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class BoardService {
    @Autowired BoardMapper boardMapper;
    @Autowired BoardFileMapper boardFileMapper;

    // 게시글 개수 카운트
    public int getBoardCount(String category, String search) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("search", search);

        return boardMapper.selectBoardCount(map);
    }

    // 게시판 상세정보 조회
    public Map<String, Object> getBoardOne(int boardNo) {
        return boardMapper.selectBoardOne(boardNo);
    }

    // 조회수 증가
    public void increaseViews(int boardNo) {
        boardMapper.increaseViews(boardNo);
    }

    // 게시판 리스트 조회 및 검색
    public List<Map<String, Object>> getBoardList(String category, String search, int currentPage, int rowPerPage) {
        int beginRow = (currentPage-1)*rowPerPage;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("search", search);
        map.put("beginRow", beginRow);
        map.put("rowPerPage", rowPerPage);
        return boardMapper.selectBoardList(map);
    }

    // 댓글, 대댓글


    // 파일 업로드, 다운로드

    // 게시글 입력
    public int addBoard(BoardFormDTO boardFormDTO, String path) {
        // 1+2+3+4 -> 트랜잭션 처리

        // board 테이블(1) : (N) board_file 테이블

        // 1. board mapper 호출(insert -> key 반환)
        int boardNo = 0; // insert 후 key 반환값

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setWriter(boardFormDTO.getWriter());
        boardDTO.setTitle(boardFormDTO.getTitle());
        boardDTO.setContent(boardFormDTO.getContent());

        int row = boardMapper.insertBoard(boardDTO);

        // boardFormDTO에 path 값과 boardNo 값 세팅
        boardFormDTO.setPath(path);
        boardNo = boardDTO.getBoardNo();

        // 파일이 하나 이상일 때
        if(boardFormDTO.getFiles().get(0).getSize() > 0 && row == 1) {
            // 2. 파일 업로드
            List<MultipartFile> files = boardFormDTO.getFiles();

            if(files != null) {
                for(MultipartFile mf : files) {
                    // 3. 파일 저장
                    BoardFileDTO boardFileDTO = new BoardFileDTO();

                    String originName = mf.getOriginalFilename();

                    String fileName = UUID.randomUUID().toString().replace("-",""); // 확장자 미포함

                    String ext = originName.substring(originName.lastIndexOf(".")+1);

                    fileName = fileName + "." + ext;

                    File f = new File(boardFormDTO.getPath() + fileName); // 풀네임으로 빈파일을 생성
                    // 빈파일에 mf안의 업로드된 파일을 복사

                    boardFileDTO.setBoardNo(boardNo);
                    boardFileDTO.setOriginName(originName);
                    boardFileDTO.setFileName(fileName);
                    boardFileDTO.setFileType(mf.getContentType());
                    boardFileDTO.setFileSize(mf.getSize());

                    // 4. 파일정보를 board_file 테이블에 저장
                    row = boardFileMapper.insertBoardFile(boardFileDTO);

                    try {
                        mf.transferTo(f);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 파일업로드에 실패하면
                        // try...catch절이 필요로 하지 않는 RuntimeException을 발생시켜서
                        // 애노테이션Transactional이 감지하여 rollback할 수 있도록
                        throw new RuntimeException();
                    }
                }
            }
        }

        return row;
    }

    // 게시글 수정
    public int modifyBoard(BoardDTO boardDTO) {
        return boardMapper.updateBoard(boardDTO);
    }

    // 게시글 삭제
    public int removeBoard(int boardNo) {
        return boardMapper.deleteBoard(boardNo);
    }
}
