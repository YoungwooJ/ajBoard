package com.example.demo.domain;

import lombok.Data;

@Data
public class BoardDTO {
    private int boardNo;
    private String title;
    private String content;
    private String writer;
    private int views;
    private String createdate;
    private String updatedate;
    private String remvFlag;
}
