package com.example.demo.service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardFileDTO;
import com.example.demo.domain.BoardFormDTO;
import com.example.demo.domain.MemberDTO;
import com.example.demo.repository.BoardFileMapper;
import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class BoardService {
    @Autowired BoardMapper boardMapper;
    @Autowired BoardFileMapper boardFileMapper;
    @Autowired CommentMapper commentMapper;

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

    // 쿠키 확인 후 조회수 증가
    public void increaseViewsWithCookie(HttpSession session, HttpServletRequest request, HttpServletResponse response, String id, int boardNo, String writer) {
        MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
        Cookie viewCookie = null;
        Cookie[] cookies=request.getCookies();

        if(cookies !=null) {
            for (int i = 0; i < cookies.length; i++) {
                //만들어진 쿠키들을 확인하며, 만약 들어온 적 있다면 생성되었을 쿠키가 있는지 확인
                if(cookies[i].getName().equals("|"+id+boardNo+"|")) {
                    //찾은 쿠키를 변수에 저장
                    viewCookie=cookies[i];
                }
            }
        }
        //만들어진 쿠키가 없음을 확인
        if(viewCookie==null) {
            try {
                //이 페이지에 왔다는 증거용 쿠키 생성
                Cookie newCookie=new Cookie("|"+id+boardNo+"|","readCount");
                response.addCookie(newCookie);
                //쿠키가 없으니 증가 로직 진행 + 타인의 게시물일 때만 증가
                if(!loginMember.getId().equals(writer)) {
                    boardMapper.increaseViews(boardNo);
                }
                // 쿠키 유지시간을 오늘 하루 자정까지로 설정
                long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
                long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
                viewCookie.setPath("/"); // 모든 경로에서 접근 가능
                viewCookie.setMaxAge((int) (todayEndSecond - currentSecond));
                response.addCookie(viewCookie);
            } catch (Exception e) {
                e.getStackTrace();
            }
            //만들어진 쿠키가 있으면 증가로직 진행하지 않음
        }
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

    // 게시글 입력, 파일 업로드
    public String addBoard(BoardFormDTO boardFormDTO, String path) {
        String address = null;
        try {
            // 내용이 없을 시
            if(boardFormDTO.getContent().equals("") || boardFormDTO.getContent() == null) {
                String msg = URLEncoder.encode("내용을 입력해주세요.", "UTF-8");
                return "redirect:/board/addBoard?boardNo="+boardFormDTO.getBoardNo()+"&msg="+msg;
            }

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

                if (files != null) {
                    for (MultipartFile mf : files) {
                        // 3. 파일 저장
                        BoardFileDTO boardFileDTO = new BoardFileDTO();

                        String originName = mf.getOriginalFilename();

                        String fileName = UUID.randomUUID().toString().replace("-", ""); // 확장자 미포함

                        String ext = originName.substring(originName.lastIndexOf(".") + 1);

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
                        // row != 0 이면 입력 성공
                        if (row == 0) {
                            String msg = URLEncoder.encode("등록 실패하였습니다.", "UTF-8");
                            address = "redirect:/board/addBoard?boardNo=" + boardFormDTO.getBoardNo() + "&msg=" + msg;
                        }
                        String msg = URLEncoder.encode("게시물을 등록하였습니다.", "UTF-8");
                        address = "redirect:/board/getBoardList?msg=" + msg;

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    // 게시글 수정
    public String modifyBoard(BoardDTO boardDTO) {
        String address = null;
        try {
            // 방어코드
            if(boardDTO.getContent().equals("") || boardDTO.getContent() == null) {
                String msg = URLEncoder.encode("내용을 입력해주세요.", "UTF-8");
                address = "redirect:/board/modifyBoard?boardNo="+boardDTO.getBoardNo()+"&msg="+msg;
            } else {
                int row = boardMapper.updateBoard(boardDTO);
                // row != 0 이면 수정 성공
                if (row == 0) {
                    String msg = URLEncoder.encode("수정 실패하였습니다.", "UTF-8");
                    address = "redirect:/board/modifyBoard?boardNo=" + boardDTO.getBoardNo() + "&msg=" + msg;
                } else {
                    String msg = URLEncoder.encode("게시물을 수정하였습니다.", "UTF-8");
                    address = "redirect:/board/getBoardOne?boardNo=" + boardDTO.getBoardNo() + "&msg=" + msg;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    // 게시글 삭제
    public String removeBoard(int boardNo) {
        String address = null;
        try {
            if(boardNo != 0) {
                int row = boardMapper.deleteBoard(boardNo);
                if (row == 0) {
                    String msg = URLEncoder.encode("게시물 삭제 실패하였습니다.", "UTF-8");
                    address = "redirect:/board/getBoardOne?boardNo=" + boardNo + "&msg=" + msg;
                } else {
                    int fileRow = boardFileMapper.deleteBoardFile(boardNo);
                    if (fileRow == 0) {
                        String msg = URLEncoder.encode("파일 삭제 실패하였습니다.", "UTF-8");
                        address = "redirect:/board/getBoardOne?boardNo=" + boardNo + "&msg=" + msg;
                    } else {
                        int commentRow = commentMapper.deleteCommentForDeleteBoard(boardNo);
                        if (commentRow == 0) {
                            String msg = URLEncoder.encode("댓글 삭제 실패하였습니다.", "UTF-8");
                            address = "redirect:/board/getBoardOne?boardNo=" + boardNo + "&msg=" + msg;
                        } else {
                            String msg = URLEncoder.encode("삭제 성공하였습니다.", "UTF-8");
                            address = "redirect:/board/getBoardList?msg=" + msg;
                        }
                    }
                }
            } else {
                String msg = URLEncoder.encode("삭제할 게시물이 없습니다.", "UTF-8");
                address = "redirect:/board/getBoardOne?boardNo=" + boardNo + "&msg=" + msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
}
