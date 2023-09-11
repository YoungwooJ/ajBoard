package com.example.demo.domain;

import lombok.Data;

@Data
public class BoardFileDTO {
    private int boardFileNo;
    private int boardNo;
    private String originName;
    private String fileName;
    private String fileType;
    private long fileSize;
    private String createdate;
    private String remvFlag;
}
