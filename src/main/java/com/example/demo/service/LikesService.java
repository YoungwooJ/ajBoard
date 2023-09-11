package com.example.demo.service;

import com.example.demo.domain.LikesDTO;
import com.example.demo.repository.LikesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class LikesService {
    @Autowired LikesMapper likesMapper;

    // 좋아요 삭제
    public int removeLikes (int likesNo) {
        return likesMapper.deleteLikes(likesNo);
    }

    // 좋아요 추가
    public int addLikes (LikesDTO likesDTO) {
        return likesMapper.insertLikes(likesDTO);
    }

    // 좋아요 여부
    public LikesDTO getLikesOne (int boardNo, int commentNo, String id) {
        return likesMapper.selectLikesOne(boardNo, commentNo, id);
    }

    // 좋아요 출력
    public int getLikesCount (int boardNo, int commentNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardNo", boardNo);
        map.put("commentNo", commentNo);
        return likesMapper.selectLikesCount(map);
    }
}
