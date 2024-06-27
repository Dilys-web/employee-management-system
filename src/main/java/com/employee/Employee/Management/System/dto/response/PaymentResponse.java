package com.employee.Employee.Management.System.dto.response;

import java.util.Map;

public record PaymentResponse(String status, String message, Map<String, Object> data) {
}
