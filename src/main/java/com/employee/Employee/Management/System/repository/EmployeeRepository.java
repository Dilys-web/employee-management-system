package com.employee.Employee.Management.System.repository;

import com.employee.Employee.Management.System.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// type of entity and type of primary key are passed
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByFirstNameContaining(String firstName);
    List<Employee> findBySecondNameContaining(String secondName);

}
