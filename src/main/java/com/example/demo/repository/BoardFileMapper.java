package com.example.demo.repository;

import com.example.demo.domain.BoardFileDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardFileMapper {

    // 게시글 파일 삭제
    int deleteBoardFile(int boardFileNo);

    // 게시글 파일 수정
    int updateBoardFile(BoardFileDTO boardFileDTO);

    // 게시글 파일 등록
    int insertBoardFile(BoardFileDTO boardFileDTO);

    // 게시글에 맞는 게시글 이미지 상세정보 출력
    BoardFileDTO selectBoardFileOne(int boardFileNo);

    // 게시글에 맞는 게시글 이미지 목록 출력
    List<BoardFileDTO> selectBoardFileList(int boardNo);
}
