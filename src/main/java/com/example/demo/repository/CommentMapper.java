package com.example.demo.repository;

import com.example.demo.domain.CommentDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    // 댓글 개수
    int selectCommentCount(int boardNo);

    // 댓글 삭제 시 대댓글 삭제
    int deleteReplyForDeleteComment(int cgroup);

    // 게시물 삭제 시 댓글 삭제
    int deleteCommentForDeleteBoard(int boardNo);

    // 댓글 삭제
    int deleteComment(int commentNo);

    // 댓글 수정
    int updateComment(CommentDTO commentDTO);

    // 대댓글 입력
    int insertReply(CommentDTO commentDTO);

    // 댓글 입력
    int insertComment(CommentDTO commentDTO);

    // 대댓글 출력
    List<CommentDTO> selectReplyList(int boardNo);

    // 댓글 출력
    CommentDTO selectCommentOne(int commentNo);
    List<CommentDTO> selectCommentList(int boardNo);
}
