package com.example.response;

import lombok.Data;

@Data
public class EmployeeDetails {
    public String email;
    public Integer verificationCode;
    public String resetPassword;
}
