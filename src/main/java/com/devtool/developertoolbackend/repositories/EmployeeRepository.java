package com.devtool.developertoolbackend.repositories;

import com.devtool.developertoolbackend.valueobjects.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public boolean existsByEmail(String mail);
    public boolean existsByName(String name);

    public Employee findByName(String name);
}

