package com.example.demo.domain;

import lombok.Data;

@Data
public class LikesDTO {
    private int likesNo;
    private String id;
    private int boardNo;
    private int commentNo;
    private String createdate;
}
