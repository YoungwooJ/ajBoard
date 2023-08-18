package com.example.demo.service;

import com.example.demo.domain.BoardFileDTO;
import com.example.demo.repository.BoardFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BoardFileService {

    @Autowired BoardFileMapper boardFileMapper;

    // 게시글 파일 삭제
    public int removeBoardFile(int boardFileNo) {
        return boardFileMapper.deleteBoardFile(boardFileNo);
    };

    // 게시글 파일 수정
    public int modifyBoardFile(BoardFileDTO boardFileDTO) {
        return boardFileMapper.updateBoardFile(boardFileDTO);
    };

    // 게시글 파일 등록
    public int addBoardFile(List<MultipartFile> files, String path, int boardNo) {
        // 1+2+3 -> 트랜잭션 처리
        int row = 0;

        // board 테이블(1) : (N) board_file 테이블

        // 1. 파일 업로드
        if(files.get(0).getSize() > 0) {
            for(MultipartFile mf : files) {
                // 2. 파일 저장
                BoardFileDTO boardFileDTO = new BoardFileDTO();

                String originName = mf.getOriginalFilename();
                String fileName = UUID.randomUUID().toString().replace("-", ""); // 확장자 미포함
                String ext = originName.substring(originName.lastIndexOf(".")+1);

                fileName = fileName + "." + ext;

                File f = new File(path + fileName); // 풀네임으로 빈파일을 생성
                // 빈파일에 mf안의 업로드된 파일을 복사

                boardFileDTO.setBoardNo(boardNo);
                boardFileDTO.setOriginName(originName);
                boardFileDTO.setFileName(fileName);
                boardFileDTO.setFileType(mf.getContentType());
                boardFileDTO.setFileSize(mf.getSize());

                // 3. 파일정보를 boardFile 테이블에 저장
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
        return row;
    };

    // 게시글에 맞는 게시글 이미지 상세정보 출력
    public BoardFileDTO getBoardFileOne(int boardFileNo) {
        return boardFileMapper.selectBoardFileOne(boardFileNo);
    };

    // 게시글에 맞는 게시글 이미지 목록 출력
    public List<BoardFileDTO> getBoardFileList(int boardNo) {
        return boardFileMapper.selectBoardFileList(boardNo);
    };
}
