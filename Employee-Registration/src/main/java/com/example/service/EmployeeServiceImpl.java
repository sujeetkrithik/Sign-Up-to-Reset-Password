package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.entity.LoginRequest;
import com.example.repository.EmployeeRepository;
import com.example.response.EmployeeDetails;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    // mail code
    private final JavaMailSender javaMailSender;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, JavaMailSender javaMailSender) {
        this.employeeRepository = employeeRepository;
        this.javaMailSender = javaMailSender;
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

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employee1.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));

        employee1.setMobile(employeeDTO.getMobile());
        employee1.setGender(employeeDTO.getGender());
        employee1.setDob(employeeDTO.getDob());

        employeeRepository.save(employee1);
        return employee1;
    }

    //Login Code
    @Override
    public Object loggedinEmployee(LoginRequest loginRequest) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String email = loginRequest.getEmail();
        long mobile = loginRequest.getMobile();
        String password = passwordEncoder.encode(loginRequest.getPassword());

        if(employeeRepository.existsEmployeeByEmail(email) || employeeRepository.existsEmployeeByMobile(mobile)){
            Employee employee = null;
            if(employeeRepository.existsEmployeeByEmail(email)){
                employee = employeeRepository.findEmployeeByEmail(email);
            }
            else if(employeeRepository.existsEmployeeByMobile(mobile)){
                employee = employeeRepository.findEmployeeByMobile(mobile);
            }
            if(password.equals(passwordEncoder.encode(employee.getPassword()))){
                System.out.println("Login is successfully completed");
            }
            return employee;
        }
        return "employee does not exist";

    }


//    //Forgot Password Code
    @Override
    public EmployeeDetails forgottedEmployeePassword(String email) {
        Employee emp = employeeRepository.findByEmail(email);

        if (Optional.ofNullable(emp).isPresent()) {
            System.out.println("your email exist");
            int min = 100000;
            int max = 999999;
            int randomWithMathRandom = (int) ((Math.random() * (max - min)) + min);
            emp.setVerificationCode(randomWithMathRandom);
            employeeRepository.save(emp);
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setEmail(emp.getEmail());
            employeeDetails.setVerificationCode(emp.getVerificationCode());
            return employeeDetails;
        }
        if (!Optional.ofNullable(emp).isPresent())
            System.out.println("your email doesn't exist");
          return null;
    }
//
    //Forgot and Reset Password Code
    @Override
    public EmployeeDetails forgotEmployeePasswordAndResetPasswprd(String email) {
        Employee emp = employeeRepository.findByEmail(email);

        if (!Optional.ofNullable(emp).isPresent()) {
            System.out.println("your email don't exist");
        }

        if (Optional.ofNullable(emp).isPresent()) {
            System.out.println("your email exist");
            int min = 100000;
            int max = 999999;
            int randomWithMathRandom = (int) ((Math.random() * (max - min)) + min);
            emp.setVerificationCode(randomWithMathRandom);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            employeeRepository.save(emp);
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setEmail(emp.getEmail());
            employeeDetails.setVerificationCode(emp.getVerificationCode());
            Scanner sc = new Scanner(System.in);
            System.out.println("enter the password:");

            String str = passwordEncoder.encode(sc.nextLine());

            emp.setResetPassword(str);
            employeeRepository.save(emp);

            employeeDetails.setResetPassword(passwordEncoder.encode(emp.getResetPassword()));

            return employeeDetails;
        }
        return null;
    }



    //Email Verification with verification code using stored data in database and send a message in email
    public Employee verifyEmployeeEmail(String email, int verificationCode){
        Employee emp = employeeRepository.findByEmail(email);

        if (Optional.ofNullable(emp).isPresent()) {
            System.out.println("your email exist");
        }
//         public void sendEmail {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("krithiksujeet@gmail.com");
        mail.setTo(emp.getEmail());
        mail.setSubject("verify the email");
        System.out.println("A verification code has sent to your email:");

        if (verificationCode == emp.getVerificationCode()){
            mail.setText("The email has been verified");
            javaMailSender.send(mail);
//            }
            return emp;
        }
        return null;
    }



}
