package com.example.demo.controller;

import com.example.demo.domain.CommentDTO;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

@Controller
public class CommentController {
    @Autowired CommentService commentService;

    // 댓글 삭제
    @GetMapping("/comment/removeComment")
    public String removeComment(Model model, @RequestParam(value="commentNo", required = true) int commentNo
                                , @RequestParam(value="boardNo", required = true) int boardNo) throws Exception {
        int row = commentService.removeComment(commentNo);
        if(row ==0){
            String msg = URLEncoder.encode("삭제 실패하였습니다.", "UTF-8");
            return "redirect:/board/getBoardOne?boardNo="+boardNo+"&msg="+msg;
        }
        String msg = URLEncoder.encode("댓글을 삭제하였습니다.", "UTF-8");
        return "redirect:/board/getBoardOne?boardNo="+boardNo+"&msg="+msg;
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
    public String modifyComment(Model model, CommentDTO commentDTO) throws Exception {
        int row = commentService.modifyComment(commentDTO);
        if(row ==0){
            String msg = URLEncoder.encode("수정 실패하였습니다.", "UTF-8");
            return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;
        }
        String msg = URLEncoder.encode("댓글을 수정하였습니다.", "UTF-8");
        return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;
    }

    // 대댓글 입력
    @PostMapping("/comment/addReply")
    public String addReply(CommentDTO commentDTO) throws Exception {
        if(commentDTO.getComment().equals("") || commentDTO.getComment() == null){
            String commentMsg = URLEncoder.encode("댓글을 입력해주세요.", "UTF-8");
            return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&commentMsg="+commentMsg;
        }
        int row = commentService.addReply(commentDTO);
        if(row ==0){
            String msg = URLEncoder.encode("등록 실패하였습니다.", "UTF-8");
            return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;
        }
        String msg = URLEncoder.encode("댓글을 등록하였습니다.", "UTF-8");
        return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;
    }

    // 댓글 입력
    @PostMapping("/comment/addComment")
    public String addComment(CommentDTO commentDTO) throws Exception {
        int row = commentService.addComment(commentDTO);
        if(row ==0){
            String msg = URLEncoder.encode("등록 실패하였습니다.", "UTF-8");
            return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;
        }
        String msg = URLEncoder.encode("댓글을 등록하였습니다.", "UTF-8");
        return "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;
    }
}
