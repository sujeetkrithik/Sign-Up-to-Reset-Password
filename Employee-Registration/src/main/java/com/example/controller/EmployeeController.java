package com.example.controller;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.entity.LoginRequest;
import com.example.response.EmployeeDetails;
import com.example.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee/add")
    public Employee addEmployee(@RequestBody EmployeeDTO employeeDTO){
      return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping("/employee/get")
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employee/get/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") int employeeId){
        return employeeService.getEmployeeByEmployeeId(employeeId);
    }

    @PutMapping("/employee/update/{employeeId}")
    public Employee updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable int employeeId){
        return employeeService.editEmployee(employeeDTO, employeeId);
    }

    // Login code
    @PostMapping("/employee/login")
    private Object loginEmployee(@RequestBody LoginRequest loginRequest) {
        return employeeService.loggedinEmployee(loginRequest);
    }

    //Forgot password
    @PostMapping("/employee/forgot/{email}")
    private EmployeeDetails forgetEmployeePassword(@PathVariable("email") String email){
        return employeeService.forgottedEmployeePassword(email);
    }

    // Forgot and Reset password
    @PostMapping("/employee/forgot/reset/{email}")
    public EmployeeDetails resetEmployeePassword(@PathVariable("email") String email)
    {
        return employeeService.forgotEmployeePasswordAndResetPasswprd(email);
    }


    //Email Verification with verification code using stored data in database and send a message in email
    @PostMapping("/employee/verification/{email}/{verification_code}")
    private Employee verifyEmployee(@PathVariable("email") String email, @RequestParam int verificationCode){
        return employeeService.verifyEmployeeEmail(email, verificationCode);
    }
}
