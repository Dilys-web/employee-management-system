package com.employee.Employee.Management.System.mapper;

import com.employee.Employee.Management.System.dto.request.EmployeeDto;
import com.employee.Employee.Management.System.entity.Employee;

public class EmployeeMapper {
    // map employee entity to employee dto
    public static EmployeeDto mapToEmployee(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getSecondName(),
                employee.getEmail(),
                employee.getEmployeeAddress(),
                employee.getEmployeePhone()
        );
    }

    // map employyedto to employee
    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getSecondName(),
                employeeDto.getEmail(),
                employeeDto.getEmployeeAddress(),
                employeeDto.getEmployeePhone()

        );
    }
}
