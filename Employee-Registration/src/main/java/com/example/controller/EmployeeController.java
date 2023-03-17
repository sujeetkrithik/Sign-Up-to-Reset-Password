package com.example.controller;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
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
}
