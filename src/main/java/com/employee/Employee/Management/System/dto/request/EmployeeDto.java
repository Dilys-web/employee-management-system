package com.employee.Employee.Management.System.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Address should not be empty")
    @Size(max=255)
    private String employeeAddress;
    @Pattern(regexp = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$", message = "Invalid phone number format")
    private String employeePhone;
}
