package com.employee.Employee.Management.System.controller;

import com.employee.Employee.Management.System.dto.EmployeeDto;
import com.employee.Employee.Management.System.entity.Employee;
import com.employee.Employee.Management.System.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    //Build Add Employee REST API

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee((employeeDto));
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);

    }
    // Build Get Employees REST API
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeId(Long employeeId){
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

}
