package com.example.demo.repository;

import com.example.demo.domain.LikesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface LikesMapper {

    // 좋아요 삭제
    int deleteLikes (int likesNo);

    // 좋아요 추가
    int insertLikes (LikesDTO likesDTO);

    // 좋아요 여부
    LikesDTO selectLikesOne (@Param("boardNo") int boardNo, @Param("commentNo") int commentNo, @Param("id") String id);

    // 좋아요 출력
    int selectLikesCount (HashMap<String, Object> map);
}
