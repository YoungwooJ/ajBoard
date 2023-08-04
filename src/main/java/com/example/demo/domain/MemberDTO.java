package com.example.demo.domain;

import lombok.Data;

@Data
public class MemberDTO {
    private String id;
    private String password;
    private String name;
    private String birth;
    private String gender;
    private String phone;
    private String email;
}
