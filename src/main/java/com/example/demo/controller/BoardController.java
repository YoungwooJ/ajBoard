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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            /*for (CommentDTO c : commentList) {
                int commentNo = c.getCommentNo();
                LikesDTO l = likesService.getLikesOne(0, commentNo, id);
                commentLikesList.add(l);

                int commentCount = likesService.getLikesCount(0, commentNo);
                commentLikesCount.add(commentCount);
            }
            model.addAttribute("commentLikesCount", commentLikesCount);
            model.addAttribute("commentLikesList", commentLikesList);*/
        }

        // 대댓글 목록
        List<CommentDTO> replyList = commentService.getReplyList(boardNo);
        if(replyList != null){
            model.addAttribute("replyList", replyList);
            /*for (CommentDTO r : replyList) {
                int commentNo = r.getCommentNo();
                LikesDTO l = likesService.getLikesOne(0, commentNo, id);
                replyLikesList.add(l);

                int replyCount = likesService.getLikesCount(0, commentNo);
                replyLikesCount.add(replyCount);
            }
            model.addAttribute("replyLikesCount", replyLikesCount);
            model.addAttribute("replyLikesList", replyLikesList);*/
        }

        // 댓글 개수
        int countComment = commentService.getCommentCount(boardNo);
        model.addAttribute("countComment", countComment);

        // 조회수 증가
        String writer = (String)map.get("WRITER");
        boardService.increaseViewsWithCookie(session, request, response, id, boardNo, writer);

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

        model.addAttribute("boardList", boardService.getBoardList(category, search, currentPage, rowPerPage));
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
        model.addAttribute("loginMember", session.getAttribute("loginMember"));
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}

        return "/board/addBoard";
    }
    @PostMapping("/board/addBoard")
    public String addBoard(HttpServletRequest request, BoardFormDTO boardFormDTO) {
        String path = request.getServletContext().getRealPath("/boardFile-upload/");
        return boardService.addBoard(boardFormDTO, path);
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
    public String modifyBoard(BoardDTO boardDTO) {
        return boardService.modifyBoard(boardDTO);
    }

    // 게시글 삭제
    @GetMapping("/board/removeBoard")
    public String removeBoard(@RequestParam(value = "boardNo", required = true) int boardNo) {
        return boardService.removeBoard(boardNo);
    }
}
