package com.employee.Employee.Management.System.mapper;

import com.employee.Employee.Management.System.dto.EmployeeDto;
import com.employee.Employee.Management.System.entity.Employee;

public class EmployeeMapper {
    // map employee entity to employee dto
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getSecondName(),
                employee.getEmail()
        );
    }

    // map employyedto to employee
    public static Employee mapToEmployeeDto(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getSecondName(),
                employeeDto.getEmail()

        );
    }
}
