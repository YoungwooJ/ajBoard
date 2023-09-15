package com.example.demo.service;

import com.example.demo.domain.CommentDTO;
import com.example.demo.repository.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
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
    public String removeComment(int boardNo, int commentNo) {
        String address = null;
        try {
            if(boardNo != 0 && commentNo != 0) {
                CommentDTO commentDTO = commentMapper.selectCommentOne(commentNo);
                // 만약 삭제할 댓글이 댓글이라면
                if(commentDTO.getCdepth() == 0) {
                    int cgroup = commentDTO.getCommentNo();
                    // 댓글의 달려있는 대댓글 삭제
                    int replyRow = commentMapper.deleteReplyForDeleteComment(cgroup);
                    if (replyRow == 0) {
                        String msg = URLEncoder.encode("삭제 실패하였습니다.", "UTF-8");
                        address = "redirect:/board/getBoardOne?boardNo=" + boardNo + "&msg=" + msg;
                    } else {
                        // 댓글 삭제
                        int commentRow = commentMapper.deleteComment(commentNo);
                        String msg;
                        if (commentRow == 0) {
                            msg = URLEncoder.encode("삭제 실패하였습니다.", "UTF-8");
                        } else {
                            msg = URLEncoder.encode("댓글을 삭제하였습니다.", "UTF-8");
                        }
                        address = "redirect:/board/getBoardOne?boardNo=" + boardNo + "&msg=" + msg;
                    }
                } else {// 만약 삭제할 댓글이 대댓글이라면
                    int row = commentMapper.deleteComment(commentNo);
                    String msg;
                    if (row == 0) {
                        msg = URLEncoder.encode("삭제 실패하였습니다.", "UTF-8");
                    } else {
                        msg = URLEncoder.encode("댓글을 삭제하였습니다.", "UTF-8");
                    }
                    address = "redirect:/board/getBoardOne?boardNo=" + boardNo + "&msg=" + msg;
                }
            } else {
                String msg = URLEncoder.encode("삭제할 댓글이 없습니다.", "UTF-8");
                address = "redirect:/board/getBoardList?msg=" + msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    // 댓글 수정
    public String modifyComment(CommentDTO commentDTO) {
        String address = null;
        try {
            if(commentDTO != null && !"".equals(commentDTO)) {
                int row = commentMapper.updateComment(commentDTO);
                if (row == 0) {
                    String msg = URLEncoder.encode("수정 실패하였습니다.", "UTF-8");
                    address = "redirect:/board/getBoardOne?boardNo=" + commentDTO.getBoardNo() + "&msg=" + msg;
                }
                String msg = URLEncoder.encode("댓글을 수정하였습니다.", "UTF-8");
                address = "redirect:/board/getBoardOne?boardNo=" + commentDTO.getBoardNo() + "&msg=" + msg;
            } else {
                String msg = URLEncoder.encode("수정할 댓글이 없습니다.", "UTF-8");
                address = "redirect:/board/getBoardList?msg=" + msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    // 대댓글 입력
    public String addReply(CommentDTO commentDTO) {
        String address = null;
        try {
            if(commentDTO.getComment().equals("") || commentDTO.getComment() == null){
                String commentMsg = URLEncoder.encode("댓글을 입력해주세요.", "UTF-8");
                address = "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&commentMsg="+commentMsg;
            } else {
                int row = commentMapper.insertReply(commentDTO);
                if(row ==0){
                    String msg = URLEncoder.encode("등록 실패하였습니다.", "UTF-8");
                    address = "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;
                }
                String msg = URLEncoder.encode("댓글을 등록하였습니다.", "UTF-8");
                address = "redirect:/board/getBoardOne?boardNo="+commentDTO.getBoardNo()+"&msg="+msg;}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    // 댓글 입력
    public String addComment(CommentDTO commentDTO) {
        String address = null;
        try {
            if(commentDTO != null && !"".equals(commentDTO)) {
                int row = commentMapper.insertComment(commentDTO);
                if (row == 0) {
                    String msg = URLEncoder.encode("등록 실패하였습니다.", "UTF-8");
                    address = "redirect:/board/getBoardOne?boardNo=" + commentDTO.getBoardNo() + "&msg=" + msg;
                }
                String msg = URLEncoder.encode("댓글을 등록하였습니다.", "UTF-8");
                address = "redirect:/board/getBoardOne?boardNo=" + commentDTO.getBoardNo() + "&msg=" + msg;
            } else {
                String msg = URLEncoder.encode("등록한 댓글이 없습니다.", "UTF-8");
                address = "redirect:/board/getBoardList?msg=" + msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
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
