package com.employee.Employee.Management.System.controller;

import com.employee.Employee.Management.System.dto.request.LoginDto;
import com.employee.Employee.Management.System.dto.request.RegisterDto;
import com.employee.Employee.Management.System.dto.response.AuthenticationResponse;
import com.employee.Employee.Management.System.service.interfaces.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(summary = "registering as an employee")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "authenticating employee")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody LoginDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "refresh token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}
