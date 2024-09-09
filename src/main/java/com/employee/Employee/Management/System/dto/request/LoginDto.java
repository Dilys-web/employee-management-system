package com.employee.Employee.Management.System.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "Email field is required")
    @NotEmpty(message = "Email field should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @NotBlank(message = "Password field is required")
    private String password;
}