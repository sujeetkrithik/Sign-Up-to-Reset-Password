package com.example.repository;

import com.example.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Optional<Employee> findByEmployeeId(int employeeId);
    boolean existsEmployeeByEmail(String email);


    boolean existsEmployeeByMobile(long mobile);

    Employee findEmployeeByEmail(String email);

    Employee findEmployeeByMobile(long mobile);


    Employee findByEmail(String email);
}
