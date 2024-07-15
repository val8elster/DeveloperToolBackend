package com.devtool.developertoolbackend.services;

import com.devtool.developertoolbackend.valueobjects.Employee;
import com.devtool.developertoolbackend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    public EmployeeRepository employeeRepository;

    public List<Employee> getAllUsers() {
        return employeeRepository.findAll();
    }
}

