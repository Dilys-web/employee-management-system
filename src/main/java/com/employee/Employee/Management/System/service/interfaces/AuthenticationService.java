package com.employee.Employee.Management.System.service.interfaces;

import com.employee.Employee.Management.System.dto.request.LoginDto;
import com.employee.Employee.Management.System.dto.request.RegisterDto;
import com.employee.Employee.Management.System.dto.response.AuthenticationResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterDto request);
    AuthenticationResponse authenticate(LoginDto request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException;
}