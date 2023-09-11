package com.example.demo.domain;

import lombok.Data;

@Data
public class CommentDTO {
    private int commentNo;
    private int boardNo;
    private String writer;
    private String comment;
    private int cdepth;
    private int cgroup;
    private String createdate;
    private String remvFlag;
}
