package com.employee.Employee.Management.System.controller;

import com.employee.Employee.Management.System.dto.request.EmployeeDto;
import com.employee.Employee.Management.System.exception.ResourceNotFoundException;
import com.employee.Employee.Management.System.service.interfaces.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/employees")


public class EmployeeController {
    private EmployeeService employeeService;

    // handling exception in the controller class
    @ExceptionHandler(ResourceNotFoundException.class)

    //Build Add Employee REST API

    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);

    }

    // Build Get Employees REST API
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable("id") Long employeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
//        List<EmployeeDto> employees = employeeService.getAllEmployees();
//        System.out.println(employees);
//        return ResponseEntity.ok(employees);
//    }

    @GetMapping({"/all"})
    @Operation(summary = "list all employees")
    public String listEmployees(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "list_employees";
    }

    // handler method to handle new employee request
    @GetMapping("/new")
    @Operation(summary = "Create a new employee")
    public String newEmployee(Model model) {
        // student model object to store employee form data
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employee", employeeDto);
        return "create_employee";

    }

    //handle method to handle save employee form submit request
    @PostMapping
    @Operation(summary = "")
    public String saveEmployee(@Validated @ModelAttribute("employee") EmployeeDto employeeDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employeeDto);
            return "create_employee";
        }
        employeeService.createEmployee(employeeDto);
        return "redirect:/employees";
    }

    // handler method to handle edit employ request
    @GetMapping("/{employeeId}/edit")
    public String editStudent(@PathVariable("employeeId") Long employeeId, Model model) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employeeDto);

        return "edit_employee";
    }

    //handler method to update existing employee
    @PostMapping("/{employeeId}")
    public String updateEmployee(@PathVariable("employeeId") Long employeeId,
                                 @Valid @ModelAttribute("employee") EmployeeDto employeeDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employeeDto);
            return "edit_employee";
        }
        employeeDto.setId(employeeId);
        employeeService.updateEmployee(employeeId, employeeDto);
        return "redirect:/employees";
    }

    //Handler method to handle delete student request
    @GetMapping("/employees/{employeeId}/delete")
    @Operation(summary = "delete an employee")
    public String deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "redirect:/employees";
    }

    //Handler method to handle view employee request
    @GetMapping("/{employeeId}/view")
    public String viewEmployee(@PathVariable("employeeId") Long employeeId,
                               Model model) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employeeDto);
        return "view_employee";
    }

    @GetMapping({"/"})
    @Operation(summary = "index page")
    public String index(Model model) {
        model.addAttribute("message", "Hello world");
        return "index";
    }

    // Build Update Employees REST API

    @PutMapping(value = "/{id}")
    @Operation(summary = "update employee details")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody EmployeeDto updatedEmployee) {
        EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(employeeDto);
    }


    @GetMapping("/search")
    @Operation(summary = "search for an employee")
    public ResponseEntity<List<EmployeeDto>> searchEmployees(@RequestParam(required = false) String firstName,
                                                             @RequestParam(required = false) String secondName) {
        List<EmployeeDto> employees = employeeService.searchEmployees(firstName, secondName);
        return ResponseEntity.ok(employees);
    }


    //Build Delete Employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAnEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");

    }

}
