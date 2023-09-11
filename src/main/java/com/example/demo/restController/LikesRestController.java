package com.example.demo.restController;

import com.example.demo.domain.LikesDTO;
import com.example.demo.domain.MemberDTO;
import com.example.demo.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikesRestController {
    @Autowired LikesService likesService;

    // 좋아요 삭제
    @PostMapping("/likes/removeLikes")
    public int remove(@RequestParam(value = "likesNo", required = true) int likesNo) {
        return likesService.removeLikes(likesNo);
    }

    // 좋아요 추가
    @PostMapping ("/likes/addLikes")
    public int addLikes(LikesDTO likesDTO) {
        return likesService.addLikes(likesDTO);
    }

    // 좋아요 여부
    @PostMapping("/likes/getLikesOne")
    public LikesDTO getLikesOne (@RequestParam(value = "boardNo", required = true) int boardNo
                            , @RequestParam(value = "commentNo", required = true) int commentNo
                            , @RequestParam(value = "id", required = true) String id) {
        return likesService.getLikesOne(boardNo, commentNo, id);
    }

    // 좋아요 출력
    @PostMapping("/likes/getLikesCount")
    public int getLikesCount(@RequestParam(value = "boardNo", required = true) int boardNo
            , @RequestParam(value = "commentNo", required = true) int commentNo) {
        return likesService.getLikesCount(boardNo, commentNo);
    }
}
