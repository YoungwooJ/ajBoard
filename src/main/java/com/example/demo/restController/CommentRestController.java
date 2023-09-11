package com.example.demo.restController;

import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentRestController {
    @Autowired CommentService commentService;

    // 댓글 개수
    @GetMapping("/comment/getCommentCount")
    public int getCommentCount(@RequestParam(value = "boardNo", required = true) int boardNo) {
        return commentService.getCommentCount(boardNo);
    };
}
