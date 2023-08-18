package com.example.demo.restController;

import com.example.demo.service.BoardFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardFileRestController {
    @Autowired BoardFileService boardFileService;

    // 파일 개별 삭제
    @GetMapping("/boardFile/removeBoardFileAJAX")
    public int removeBoardFile(@RequestParam(value="boardFileNo", required = true) int boardFileNo){
        return boardFileService.removeBoardFile(boardFileNo);
    }
}
