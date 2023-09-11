package com.example.demo.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardFormDTO {
    private int boardNo;
    private String title;
    private String content;
    private String writer;
    private String createdate;
    private String updatedate;
    private String remvFlag;
    
    // 멀티 파일 업로드
    private List<MultipartFile> files;

    // 파일이 저장될 폴더위치(path)
    private String path;
}
