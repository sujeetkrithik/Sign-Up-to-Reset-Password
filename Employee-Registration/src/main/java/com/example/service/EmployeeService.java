package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(EmployeeDTO employeeDTO);

    List<Employee> getAllEmployee();

    Employee getEmployeeByEmployeeId(int employeeId);

    Employee editEmployee(EmployeeDTO employeeDTO, int employeeId);
}
