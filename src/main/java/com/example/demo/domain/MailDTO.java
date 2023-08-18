package com.example.demo.domain;

import lombok.Data;

@Data
public class MailDTO {
    private String address;
    private String title;
    private String message;
}
