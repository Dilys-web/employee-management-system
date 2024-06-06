package com.employee.Employee.Management.System.service;

import com.employee.Employee.Management.System.dto.request.CreatePlanDto;
import com.employee.Employee.Management.System.dto.request.InitializePaymentDto;
import com.employee.Employee.Management.System.dto.response.CreatePlanResponse;
import com.employee.Employee.Management.System.dto.response.InitializePaymentResponse;
import com.employee.Employee.Management.System.dto.response.PaymentVerificationResponse;

public interface PaystackService {
    CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception;
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference) throws Exception;
}