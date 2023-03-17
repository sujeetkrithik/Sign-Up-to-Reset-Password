package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
       Employee employee = new Employee();
       employee.setFirstName(employeeDTO.getFirstName());
       employee.setLastName(employeeDTO.getLastName());
       employee.setEmail(employeeDTO.getEmail());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));

       employee.setMobile(employeeDTO.getMobile());
       employee.setGender(employeeDTO.getGender());
       employee.setDob(employeeDTO.getDob());
       employeeRepository.save(employee);
       return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employee = (List<Employee>) employeeRepository.findAll();
        if(employee.isEmpty()){
            throw new RuntimeException("employee is not found");
        }
        return  employee;
    }

    @Override
    public Employee getEmployeeByEmployeeId(int employeeId) {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        if (!employee.isPresent())
            throw new RuntimeException("employee is not found");

        Employee employee1 = employee.get();
        return employee1;
    }

    @Override
    public Employee editEmployee(EmployeeDTO employeeDTO, int employeeId) {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee.isEmpty())
            throw new RuntimeException("employee is not found");

        Employee employee1 = employee.get();
        employee1.setFirstName(employeeDTO.getFirstName());
        employee1.setLastName(employeeDTO.getLastName());
        employee1.setEmail(employeeDTO.getEmail());
        employee1.setPassword(employeeDTO.getPassword());
        employee1.setMobile(employeeDTO.getMobile());
        employee1.setGender(employeeDTO.getGender());
        employee1.setDob(employeeDTO.getDob());

        employeeRepository.save(employee1);
        return employee1;
    }


}
