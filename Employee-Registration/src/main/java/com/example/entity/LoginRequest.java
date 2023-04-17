package com.example.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private long mobile;
    private String password;

}
