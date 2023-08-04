package com.example.demo.domain;

import lombok.Data;

@Data
public class BoardDTO {
    private int boardNo;
    private String title;
    private String content;
    private String writer;
    private String createdate;
    private String updatedate;
}
