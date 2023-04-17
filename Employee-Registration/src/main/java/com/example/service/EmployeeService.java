package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.entity.LoginRequest;
import com.example.response.EmployeeDetails;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(EmployeeDTO employeeDTO);

    List<Employee> getAllEmployee();

    Employee getEmployeeByEmployeeId(int employeeId);

    Employee editEmployee(EmployeeDTO employeeDTO, int employeeId);

    Object loggedinEmployee(LoginRequest loginRequest);


    EmployeeDetails forgottedEmployeePassword(String email);
//
    EmployeeDetails forgotEmployeePasswordAndResetPasswprd(String email);

    Employee verifyEmployeeEmail(String email, int verificationCode);
}
