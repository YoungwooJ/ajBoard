package com.example.demo.repository;

import com.example.demo.domain.BoardDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface BoardMapper {
    // 조회수 증가
    void increaseViews(int boardNo);

    // 게시글 개수 카운트
    int selectBoardCount(HashMap<String, Object> map);

    // 게시판 상세정보 조회
    Map<String, Object> selectBoardOne(int boardNo);

    // 게시판 리스트 조회 및 검색
    List<Map<String, Object>> selectBoardList(Map<String, Object> paramMap);

    // 댓글, 대댓글


    // 파일 업로드, 다운로드

    // 게시글 입력
    int insertBoard(BoardDTO boardDTO);

    // 게시글 수정
    int updateBoard(BoardDTO boardDTO);

    // 게시글 삭제
    int deleteBoard(int boardNo);

}
