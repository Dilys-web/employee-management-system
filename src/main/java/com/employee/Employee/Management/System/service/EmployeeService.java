package com.employee.Employee.Management.System.service;

import com.employee.Employee.Management.System.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

     EmployeeDto createEmployee(EmployeeDto employeeDto);

     EmployeeDto getEmployeeById(Long employeeId);

     List<EmployeeDto> getAllEmployees();

     EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);

     void deleteEmployee(Long employeeId);

}
