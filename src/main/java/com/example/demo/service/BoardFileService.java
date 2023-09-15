package com.example.demo.service;

import com.example.demo.domain.BoardFileDTO;
import com.example.demo.repository.BoardFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BoardFileService {

    @Autowired BoardFileMapper boardFileMapper;

    // 게시글 파일 다운로드
    public void fileDownload(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 웹 어플리케이션 루트에 있는 boardFile-upload 디렉토리를 기준으로 파일 경로 생성
            String relativePath = "/boardFile-upload/";
            String fullPath = request.getServletContext().getRealPath(relativePath);

            // 파일 객체 생성
            File file = new File(fullPath, fileName);

            if (file.exists()) {
                response.setContentType("application/octet-stream");
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()) + "\"");
                response.setHeader("Content-Transfer-Encoding", "binary");
                // response 객체를 통해서 서버로부터 파일 다운로드

                try (OutputStream os = response.getOutputStream();
                     // 파일 입력 객체 생성
                     FileInputStream fis = new FileInputStream(file)) {
                    FileCopyUtils.copy(fis, os);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 게시글 파일 삭제
    public String removeBoardFile(int boardNo, int boardFileNo) {
        String address = null;
        try {
            if(boardNo != 0 && boardFileNo != 0) {
                int row = boardFileMapper.deleteBoardFile(boardFileNo);
                if (row == 0) {
                    String msg = URLEncoder.encode("삭제 실패하였습니다.", "UTF-8");
                    address = "redirect:/boardFile/getBoardFileOne?boardFileNo=" + boardFileNo + "&msg=" + msg;
                }
                String msg = URLEncoder.encode("파일을 삭제하였습니다.", "UTF-8");
                address = "redirect:/board/modifyBoard?boardNo=" + boardNo + "&msg=" + msg;
            } else {
                String msg = URLEncoder.encode("파일이 존재하지 않습니다.", "UTF-8");
                address = "redirect:/boardFile/getBoardFileOne?boardFileNo=" + boardFileNo + "&msg=" + msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    // 게시글 파일 수정
    public int modifyBoardFile(BoardFileDTO boardFileDTO) {
        return boardFileMapper.updateBoardFile(boardFileDTO);
    };

    // 게시글 파일 등록
    public String addBoardFile(List<MultipartFile> files, String path, int boardNo) {
        String address = null;
        try {
            if(files != null && boardNo != 0) {
                // 1+2+3 -> 트랜잭션 처리
                int row = 0;

                // board 테이블(1) : (N) board_file 테이블

                // 1. 파일 업로드
                if (files.get(0).getSize() > 0) {
                    for (MultipartFile mf : files) {
                        // 2. 파일 저장
                        BoardFileDTO boardFileDTO = new BoardFileDTO();

                        String originName = mf.getOriginalFilename();
                        String fileName = UUID.randomUUID().toString().replace("-", ""); // 확장자 미포함
                        String ext = originName.substring(originName.lastIndexOf(".") + 1);

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
                        // row != 0 이면 입력 성공
                        if (row == 0) {
                            String msg = URLEncoder.encode("등록실패하였습니다.", "UTF-8");
                            address = "redirect:/board/modifyBoard?boardNo=" + boardNo + "&msg=" + msg;
                        }
                        String msg = URLEncoder.encode("파일을 업로드하였습니다.", "UTF-8");
                        address = "redirect:/board/modifyBoard?boardNo=" + boardNo + "&msg=" + msg;
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
            } else {
                String msg = URLEncoder.encode("등록실패하였습니다.", "UTF-8");
                address = "redirect:/board/modifyBoard?boardNo=" + boardNo + "&msg=" + msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
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
