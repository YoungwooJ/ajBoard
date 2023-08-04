package com.example.demo.service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BoardService {
    @Autowired BoardMapper boardMapper;

    // 게시판 상세정보 조회
    public Map<String, Object> getBoardOne(int boardNo) {
        return boardMapper.selectBoardOne(boardNo);
    }

    // 게시판 리스트 조회
    public List<Map<String, Object>> getBoardList() {
        return boardMapper.selectBoardList();
    }

    // 조회수 증가


    // 게시판 검색


    // 댓글, 대댓글


    // 파일 업로드, 다운로드

    // 게시글 입력
    public int addBoard(BoardDTO boardDTO) {
        return boardMapper.insertBoard(boardDTO);
    }

    // 게시글 수정
    public int modifyBoard(BoardDTO boardDTO) {
        return boardMapper.updateBoard(boardDTO);
    }

    // 게시글 삭제
    public int removeBoard(int boardNo) {
        return boardMapper.deleteBoard(boardNo);
    }
}
