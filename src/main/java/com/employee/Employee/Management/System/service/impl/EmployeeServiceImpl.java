package com.employee.Employee.Management.System.service.impl;

import com.employee.Employee.Management.System.dto.EmployeeDto;
import com.employee.Employee.Management.System.entity.Employee;
import com.employee.Employee.Management.System.exception.ResourceNotFoundException;
import com.employee.Employee.Management.System.mapper.EmployeeMapper;
import com.employee.Employee.Management.System.repository.EmployeeRepository;
import com.employee.Employee.Management.System.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployeeDto(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);


    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResolutionException("Employee does not exist with given id : " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Cacheable(cacheNames = "employees")
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List <Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "employees", key = "#employeeId")
    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
     Employee employee = employeeRepository.findById(employeeId). orElseThrow(
                ()-> new ResourceNotFoundException("Employee does not exist with given id : " + employeeId)
        );
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setSecondName(updatedEmployee.getSecondName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResolutionException("Employee does not exist with given id : " + employeeId));
        employeeRepository.delete(employee);
    }

}
