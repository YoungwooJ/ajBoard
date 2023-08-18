package com.example.demo.controller;

import com.example.demo.domain.CommentDTO;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @Autowired CommentService commentService;

    // 댓글 삭제
    @GetMapping("/comment/removeComment")
    public String removeComment(Model model, @RequestParam(value="commentNo", required = true) int commentNo
                                , @RequestParam(value="boardNo", required = true) int boardNo) {
        int row = commentService.removeComment(commentNo);
        if(row ==0){
            model.addAttribute("msg", "삭제 실패하였습니다.");
            return "redirect:/board/getBoardOne?boardNo="+boardNo;
        } else {
            model.addAttribute("msg", "댓글을 삭제하였습니다.");
        };
        return "redirect:/board/getBoardOne?boardNo="+boardNo;
    }

    // 댓글 수정
    @GetMapping("/comment/modifyComment")
    public String modifyComment(Model model, @RequestParam(value="commentNo", required = true) int commentNo
                                , @RequestParam(value="boardNo", required = true) int boardNo) {
        CommentDTO comment = commentService.getCommentOne(commentNo);
        model.addAttribute("comment", comment);
        model.addAttribute("boardNo", boardNo);

        return "/comment/modifyComment";
    }
    @PostMapping("/comment/modifyComment")
    public String modifyComment(Model model, CommentDTO commentDTO) {
        int row = commentService.modifyComment(commentDTO);
        if(row ==0){
            model.addAttribute("msg", "삭제 실패하였습니다.");
            return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo();
        } else {
            model.addAttribute("msg", "댓글을 삭제하였습니다.");
        };
        return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo();
    }

    // 대댓글 입력
    @PostMapping("/comment/addReply")
    public String addReply(Model model, CommentDTO commentDTO) {
        int row = commentService.addReply(commentDTO);
        if(row ==0){
            model.addAttribute("msg", "등록 실패하였습니다.");
            return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo();
        } else {
            model.addAttribute("msg", "댓글을 등록하였습니다.");
        };
        return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo();
    }

    // 댓글 입력
    @PostMapping("/comment/addComment")
    public String addComment(Model model, CommentDTO commentDTO) {
        int row = commentService.addComment(commentDTO);
        if(row ==0){
            model.addAttribute("msg", "등록 실패하였습니다.");
            return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo();
        } else {
            model.addAttribute("msg", "댓글을 등록하였습니다.");
        };
        return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo();
    }
}
