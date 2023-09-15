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
        String address = commentService.removeComment(boardNo, commentNo);
        return address;
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
    public String modifyComment(CommentDTO commentDTO) {
        String address = commentService.modifyComment(commentDTO);
        return address;
    }

    // 대댓글 입력
    @PostMapping("/comment/addReply")
    public String addReply(CommentDTO commentDTO) {
        String address = commentService.addReply(commentDTO);
        return address;
    }

    // 댓글 입력
    @PostMapping("/comment/addComment")
    public String addComment(CommentDTO commentDTO) {
        String address = commentService.addComment(commentDTO);
        return address;
    }
}
