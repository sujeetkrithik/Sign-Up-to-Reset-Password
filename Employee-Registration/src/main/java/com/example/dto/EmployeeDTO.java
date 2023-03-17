package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long mobile;
    private String gender;
    private String dob;

}
