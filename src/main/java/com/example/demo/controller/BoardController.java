package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.BoardFileService;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired BoardService boardService;
    @Autowired BoardFileService boardFileService;
    @Autowired CommentService commentService;
    @Autowired LikesService likesService;

    // 게시판 상세정보, 댓글, 대댓글 조회 및 조회수 증가
    @GetMapping("/board/getBoardOne")
    public String getBoardOne(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response
                                , @RequestParam(value = "boardNo", required = true) int boardNo
                                , @RequestParam(value = "msg", required = false) String msg
                                , @RequestParam(value = "commentMsg", required = false) String commentMsg) {

        // 세션 회원
        MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);
        String id = loginMember.getId();
        model.addAttribute("id", id);

        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}
        if(commentMsg != null) {model.addAttribute("commentMsg", commentMsg);}

        // 게시물 내용
        Map<String, Object> map = boardService.getBoardOne(boardNo);
        model.addAttribute("board", map);

        // 게시물 파일
        List<BoardFileDTO> list = boardFileService.getBoardFileList(boardNo);
        if(list != null){model.addAttribute("files", list);}

        // 좋아요 목록
        LikesDTO boardLikes = likesService.getLikesOne(boardNo, 0, id);
        model.addAttribute("boardLikes", boardLikes);

        List<LikesDTO> commentLikesList = new ArrayList<LikesDTO>();
        List<LikesDTO> replyLikesList = new ArrayList<LikesDTO>();

        // 좋아요 개수
        int boardLikesCount = likesService.getLikesCount(boardNo, 0);
        model.addAttribute("boardLikesCount", boardLikesCount);

        List<Integer> commentLikesCount = new ArrayList<Integer>();
        List<Integer> replyLikesCount = new ArrayList<Integer>();

        // 댓글 목록
        List<CommentDTO> commentList = commentService.getCommentList(boardNo);
        if(commentList != null){
            model.addAttribute("commentList", commentList);
            for (CommentDTO c : commentList) {
                int commentNo = c.getCommentNo();
                LikesDTO l = likesService.getLikesOne(0, commentNo, id);
                commentLikesList.add(l);

                int commentCount = likesService.getLikesCount(0, commentNo);
                commentLikesCount.add(commentCount);
            }
            model.addAttribute("commentLikesCount", commentLikesCount);
            model.addAttribute("commentLikesList", commentLikesList);
        }

        // 대댓글 목록
        List<CommentDTO> replyList = commentService.getReplyList(boardNo);
        if(replyList != null){
            model.addAttribute("replyList", replyList);
            for (CommentDTO r : replyList) {
                int commentNo = r.getCommentNo();
                LikesDTO l = likesService.getLikesOne(0, commentNo, id);
                replyLikesList.add(l);

                int replyCount = likesService.getLikesCount(0, commentNo);
                replyLikesCount.add(replyCount);
            }
            model.addAttribute("replyLikesCount", replyLikesCount);
            model.addAttribute("replyLikesList", replyLikesList);
        }

        // 댓글 개수
        int countComment = commentService.getCommentCount(boardNo);
        model.addAttribute("countComment", countComment);

        // 조회수 증가
        Cookie viewCookie=null;
        Cookie[] cookies=request.getCookies();
        System.out.println("cookie : "+cookies);

        if(cookies !=null) {
            for (int i = 0; i < cookies.length; i++) {
                //System.out.println("쿠키 이름 : "+cookies[i].getName());

                //만들어진 쿠키들을 확인하며, 만약 들어온 적 있다면 생성되었을 쿠키가 있는지 확인
                if(cookies[i].getName().equals("|"+id+"|")) {
                    System.out.println("if문 쿠키 이름 : "+cookies[i].getName());

                    //찾은 쿠키를 변수에 저장
                    viewCookie=cookies[i];
                }
            }
        }else {
            System.out.println("cookies 확인 로직 : 쿠키가 없습니다.");
        }

        //만들어진 쿠키가 없음을 확인
        if(viewCookie==null) {
            System.out.println("viewCookie 확인 로직 : 쿠키 없당");
            try {
                //이 페이지에 왔다는 증거용 쿠키 생성
                Cookie newCookie=new Cookie("|"+id+"|","readCount");
                response.addCookie(newCookie);
                //쿠키가 없으니 증가 로직 진행
                if(!loginMember.getId().equals((String) map.get("WRITER"))) {
                    boardService.increaseViews(boardNo);
                }
            } catch (Exception e) {
                System.out.println("쿠키 넣을때 오류 나나? : "+e.getMessage());
                e.getStackTrace();
            }
            //만들어진 쿠키가 있으면 증가로직 진행하지 않음
        }else {
            System.out.println("viewCookie 확인 로직 : 쿠키 있당");
            String value=viewCookie.getValue();
            System.out.println("viewCookie 확인 로직 : 쿠키 value : "+value);
        }

        return "/board/getBoardOne";
    }

    // 게시판 리스트 조회 및 검색
    @GetMapping("/board/getBoardList")
    public String getBoardList(Model model
            , @RequestParam(value = "category", required = false) String category
            , @RequestParam(value = "search", required = false) String search
            , @RequestParam(value="currentPage", defaultValue = "1") int currentPage
            , @RequestParam(value="rowPerPage", defaultValue= "10") int rowPerPage
            , @RequestParam(value = "msg", required = false) String msg) {

        List<Map<String, Object>> boardList = boardService.getBoardList(category, search, currentPage, rowPerPage);
        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("category", category);
        model.addAttribute("search", search);
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}

        int count = boardService.getBoardCount(category, search);
        int endPage = (int)Math.ceil((double)count / (double)rowPerPage);
        // 블록 페이지
        // 현재 페이지가 속한 block의 시작 번호 , 끝 번호를 계산
        int blockNum = (int)Math.floor((currentPage-1)/rowPerPage);
        int blockStartNum = (rowPerPage*blockNum) + 1;
        int blockLastNum = blockStartNum + (rowPerPage-1);

        model.addAttribute("startPage", 1);
        model.addAttribute("endPage", endPage);
        model.addAttribute("blockStartNum", blockStartNum);
        model.addAttribute("blockLastNum", blockLastNum);

        return "/board/getBoardList";
    }

    // 게시글 입력, 파일 업로드
    @GetMapping("/board/addBoard")
    public String addBoard(HttpSession session, Model model
                                , @RequestParam(value = "msg", required = false) String msg) {
        MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}

        return "/board/addBoard";
    }
    @PostMapping("/board/addBoard")
    public String addBoard(HttpServletRequest request, BoardFormDTO boardFormDTO) throws Exception {
        String path = request.getServletContext().getRealPath("/boardFile-upload/");
        String address = boardService.addBoard(boardFormDTO, path);
        return address;
    }

    // 게시글 수정
    @GetMapping("/board/modifyBoard")
    public String modifyBoard(Model model
                                , @RequestParam(value = "boardNo", required = true) int boardNo
                                , @RequestParam(value = "msg", required = false) String msg) {
        model.addAttribute("board", boardService.getBoardOne(boardNo));
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}
        // 파일이 있을시
        List<BoardFileDTO> list = boardFileService.getBoardFileList(boardNo);
        if(list != null){model.addAttribute("files", list);}

        return "/board/modifyBoard";
    }
    @PostMapping("/board/modifyBoard")
    public String modifyBoard(BoardDTO boardDTO) throws Exception {
        String address = boardService.modifyBoard(boardDTO);
        return address;
    }

    // 게시글 삭제
    @GetMapping("/board/removeBoard")
    public String removeBoard(@RequestParam(value = "boardNo", required = true) int boardNo) {
        String address = boardService.removeBoard(boardNo);
        return address;
    }
}
