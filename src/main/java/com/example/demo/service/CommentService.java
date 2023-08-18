package com.example.demo.service;

import com.example.demo.domain.CommentDTO;
import com.example.demo.repository.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired CommentMapper commentMapper;

    // 댓글 개수
    public int getCommentCount(int boardNo) {
        return commentMapper.selectCommentCount(boardNo);
    };

    // 댓글 삭제
    public int removeComment(int commentNo) {
        return commentMapper.deleteComment(commentNo);
    }

    // 댓글 수정
    public int modifyComment(CommentDTO commentDTO) {
        return commentMapper.updateComment(commentDTO);
    }

    // 대댓글 입력
    public int addReply(CommentDTO commentDTO) {
        return commentMapper.insertReply(commentDTO);
    }

    // 댓글 입력
    public int addComment(CommentDTO commentDTO) {
        return commentMapper.insertComment(commentDTO);
    }

    // 대댓글 출력
    public List<CommentDTO> getReplyList(int boardNo) {
        return commentMapper.selectReplyList(boardNo);
    };

    // 댓글 출력
    public CommentDTO getCommentOne(int commentNo) {
        return commentMapper.selectCommentOne(commentNo);
    }
    public List<CommentDTO> getCommentList(int boardNo) {
        return commentMapper.selectCommentList(boardNo);
    }
}
