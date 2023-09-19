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
    public String removeComment(@RequestParam(value="commentNo", required = true) int commentNo
                                , @RequestParam(value="boardNo", required = true) int boardNo) {
        return commentService.removeComment(boardNo, commentNo);
    }

    // 댓글 수정
    @GetMapping("/comment/modifyComment")
    public String modifyComment(Model model, @RequestParam(value="commentNo", required = true) int commentNo
                                            , @RequestParam(value="boardNo", required = true) int boardNo) {
        model.addAttribute("comment", commentService.getCommentOne(commentNo));
        model.addAttribute("boardNo", boardNo);
        return "/comment/modifyComment";
    }
    @PostMapping("/comment/modifyComment")
    public String modifyComment(CommentDTO commentDTO) {
        return commentService.modifyComment(commentDTO);
    }

    // 대댓글 입력
    @PostMapping("/comment/addReply")
    public String addReply(CommentDTO commentDTO) {
        return commentService.addReply(commentDTO);
    }

    // 댓글 입력
    @PostMapping("/comment/addComment")
    public String addComment(CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }
}
