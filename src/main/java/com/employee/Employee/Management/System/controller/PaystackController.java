package com.employee.Employee.Management.System.controller;

import com.employee.Employee.Management.System.dto.request.CreatePlanDto;
import com.employee.Employee.Management.System.dto.request.InitializePaymentDto;
import com.employee.Employee.Management.System.dto.response.CreatePlanResponse;
import com.employee.Employee.Management.System.dto.response.InitializePaymentResponse;
import com.employee.Employee.Management.System.dto.response.PaymentVerificationResponse;
import com.employee.Employee.Management.System.service.PaystackService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/paystack",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PaystackController {


    private PaystackService paystackService;

    public PaystackController(PaystackService paystackService) {
        this.paystackService = paystackService;
    }

    @PostMapping("/createplan")
    @Operation(summary = "create a plan")

    public CreatePlanResponse createPlan(@Validated @RequestBody CreatePlanDto createPlanDto) throws Exception {
        return paystackService.createPlan(createPlanDto);
    }

    @PostMapping(value = "/initializepayment")
    @Operation(summary = "initialize payment")
    public InitializePaymentResponse initializePayment( @RequestBody InitializePaymentDto initializePaymentDto) throws Throwable {
        return paystackService.initializePayment(initializePaymentDto);
    }
    @GetMapping("/initializepayment")
    @Operation(summary = "initialize payment")

    public ResponseEntity<String> welcome() throws Throwable {
        System.out.println("hello world");
        return ResponseEntity.ok("hello world");
    }

    @GetMapping("/verifypayment/{reference}")
    @Operation(summary = "verify payment")

    public PaymentVerificationResponse paymentVerification(@PathVariable(value = "reference") String reference)throws Exception {
        if (reference.isEmpty()) {
            throw new Exception("reference, plan and id must be provided in path");
        }
        return paystackService.paymentVerification(reference);
    }
}