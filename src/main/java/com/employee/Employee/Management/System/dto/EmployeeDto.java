package com.employee.Employee.Management.System.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;

    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    @NotEmpty(message = "Second name should not be empty")
    private String secondName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
}
