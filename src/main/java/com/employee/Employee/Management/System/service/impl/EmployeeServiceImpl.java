package com.employee.Employee.Management.System.service.impl;

import com.employee.Employee.Management.System.dto.request.EmployeeDto;
import com.employee.Employee.Management.System.entity.Employee;
import com.employee.Employee.Management.System.exception.ResourceNotFoundException;
import com.employee.Employee.Management.System.mapper.EmployeeMapper;
import com.employee.Employee.Management.System.repository.EmployeeRepository;
import com.employee.Employee.Management.System.service.interfaces.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployee(savedEmployee);


    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with given id : " + employeeId));
        return EmployeeMapper.mapToEmployee(employee);
    }

    @Cacheable(cacheNames = "employees")
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List <Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployee)
                .collect(Collectors.toList());
    }

//    @Cacheable(cacheNames = "employees")
//    @Override
//    public Page<EmployeeDto> getAllEmployees(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Employee> employeePage = employeeRepository.findAll(pageable);
//        return employeePage.map(EmployeeMapper::mapToEmployee);
//    }

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
        return EmployeeMapper.mapToEmployee(updatedEmployeeObj);
    }
    @Override
    public List<EmployeeDto> searchEmployees(String firstName, String secondName) {
        List<Employee> employees;
        if (firstName != null && !firstName.isEmpty()) {
            employees = employeeRepository.findByFirstNameContaining(firstName);
        } else if (secondName != null && !secondName.isEmpty()) {
            // Implement search by second name (optional)
            employees = employeeRepository.findBySecondNameContaining(secondName);
        } else {
            employees = employeeRepository.findAll(); // Handle case where no search criteria is provided (optional)
        }
        return employees.stream()
                .map(employee -> EmployeeDto.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .secondName(employee.getSecondName())
                        .email(employee.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with given id : " + employeeId));
        employeeRepository.delete(employee);
    }

}
