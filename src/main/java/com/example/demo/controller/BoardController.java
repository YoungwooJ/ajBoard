package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.BoardFileService;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired BoardService boardService;
    @Autowired BoardFileService boardFileService;
    @Autowired CommentService commentService;

    // 게시판 상세정보, 댓글, 대댓글 조회 및 조회수 증가
    @GetMapping("/board/getBoardOne")
    public String getBoardOne(Model model, HttpSession session
                                , @RequestParam(value = "boardNo", required = true) int boardNo) {

        // 게시물 내용
        Map<String, Object> map = boardService.getBoardOne(boardNo);
        model.addAttribute("board", map);

        // 게시물 파일
        List<BoardFileDTO> list = boardFileService.getBoardFileList(boardNo);
        if(list != null){
            model.addAttribute("files", list);
        }

        // 댓글 목록
        List<CommentDTO> commentList = commentService.getCommentList(boardNo);
        if(commentList != null){
            model.addAttribute("commentList", commentList);
        }

        // 대댓글 목록
        List<CommentDTO> replyList = commentService.getReplyList(boardNo);
        if(replyList != null){
            model.addAttribute("replyList", replyList);
        }

        // 댓글 개수
        int countComment = commentService.getCommentCount(boardNo);
        if(countComment != 0){
            model.addAttribute("countComment", countComment);
        }

        // 세션 회원
        MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);

        // 조회수 증가
        if(!loginMember.getId().equals((String) map.get("WRITER"))) {
            boardService.increaseViews(boardNo);
        }

        return "/board/getBoardOne";
    }

    // 게시판 리스트 조회 및 검색
    @GetMapping("/board/getBoardList")
    public String getBoardList(Model model
            , @RequestParam(value = "category", required = false) String category
            , @RequestParam(value = "search", required = false) String search
            , @RequestParam(value="currentPage", defaultValue = "1") int currentPage
            , @RequestParam(value="rowPerPage", defaultValue= "10") int rowPerPage) {

        List<Map<String, Object>> boardList = boardService.getBoardList(category, search, currentPage, rowPerPage);
        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("category", category);
        model.addAttribute("search", search);

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

    // 파일 다운로드

    // 게시글 입력, 파일 업로드
    @GetMapping("/board/addBoard")
    public String addBoard(HttpSession session, Model model) {
        MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);

        return "/board/addBoard";
    }
    @PostMapping("/board/addBoard")
    public String addBoard(Model model, HttpServletRequest request, BoardFormDTO boardFormDTO) {
        String path = request.getServletContext().getRealPath("/boardFile-upload/");

        int row = boardService.addBoard(boardFormDTO, path);
        // row != 0 이면 입력 성공
        if(row == 0) {
            model.addAttribute("msg", "등록 실패하였습니다.");
            return "redirect:/board/addBoard?boardNo="+boardFormDTO.getBoardNo();
        }

        return "redirect:/board/getBoardList";
    }

    // 게시글 수정
    @GetMapping("/board/modifyBoard")
    public String modifyBoard(Model model
                                , @RequestParam(value = "boardNo", required = true) int boardNo) {

        Map<String, Object> board = boardService.getBoardOne(boardNo);
        model.addAttribute("board", board);

        List<BoardFileDTO> list = boardFileService.getBoardFileList(boardNo);
        if(list != null){
            model.addAttribute("files", list);
        }

        return "/board/modifyBoard";
    }
    @PostMapping("/board/modifyBoard")
    public String modifyBoard(Model model, BoardDTO boardDTO) {

        int row = boardService.modifyBoard(boardDTO);
        // row != 0 이면 수정 성공
        if(row == 0) {
            model.addAttribute("msg", "수정 실패하였습니다.");
            return "redirect:/board/modifyBoard?boardNo="+boardDTO.getBoardNo();
        }

        return "redirect:/board/getBoardList";
    }

    // 게시글 삭제
    @GetMapping("/board/removeBoard")
    public String removeBoard(Model model
                                , @RequestParam(value = "boardNo", required = true) int boardNo) {

        int row = boardService.removeBoard(boardNo);
        if(row == 0) {
            model.addAttribute("msg", "삭제 실패하였습니다.");
            return "redirect:/board/getBoardOne?boardNo="+boardNo;
        } else {
            int fileRow =boardFileService.removeBoardFile(boardNo);
            if(fileRow == 0){
                model.addAttribute("msg", "삭제 실패하였습니다.");
                return "redirect:/board/getBoardOne?boardNo="+boardNo;
            } else {
                model.addAttribute("msg", "삭제 성공하였습니다.");
            }
        }

        return "redirect:/board/getBoardList";
    }

}
