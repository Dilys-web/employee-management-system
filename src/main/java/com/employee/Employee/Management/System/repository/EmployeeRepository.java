package com.employee.Employee.Management.System.repository;

import com.employee.Employee.Management.System.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// type of entity and type of primary key are passed
public interface EmployeeRespository extends JpaRepository<Employee, Long> {
}
