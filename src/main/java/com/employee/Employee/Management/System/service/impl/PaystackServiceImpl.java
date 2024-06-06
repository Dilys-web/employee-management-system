package com.employee.Employee.Management.System.service.impl;

import com.employee.Employee.Management.System.dto.request.CreatePlanDto;
import com.employee.Employee.Management.System.dto.request.InitializePaymentDto;
import com.employee.Employee.Management.System.dto.response.CreatePlanResponse;
import com.employee.Employee.Management.System.dto.response.InitializePaymentResponse;
import com.employee.Employee.Management.System.dto.response.PaymentVerificationResponse;
import com.employee.Employee.Management.System.entity.PaymentPaystack;
import com.employee.Employee.Management.System.repository.EmployeeRepository;
import com.employee.Employee.Management.System.repository.PaystackPaymentRepositoryImpl;
import com.employee.Employee.Management.System.service.PaystackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import static com.employee.Employee.Management.System.constant.APIConstants.*;

@Service
public class PaystackServiceImpl implements PaystackService {

    private final PaystackPaymentRepositoryImpl paystackPaymentRepository;
    private final EmployeeRepository employeeRepository;

    @Value("${applyforme.paystack.secret.key}")
    private String paystackSecretKey;

    public PaystackServiceImpl(PaystackPaymentRepositoryImpl paystackPaymentRepository, EmployeeRepository employeeRepository) {
        this.paystackPaymentRepository = paystackPaymentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception {
        CreatePlanResponse createPlanResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(createPlanDto));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INIT);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_CREATED) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to process payment at the moment or something wrong with request");
            }

            ObjectMapper mapper = new ObjectMapper();
            createPlanResponse = mapper.readValue(result.toString(), CreatePlanResponse.class);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return createPlanResponse;
    }

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {
        InitializePaymentResponse initializePaymentResponse = null;

        try {
//            Map<String, Object> data = new HashMap<>();
//            data.put("email", initializePaymentDto.getEmail());
//            data.put("amount", initializePaymentDto.getAmount());
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(initializePaymentDto));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to initialize payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            initializePaymentResponse = mapper.readValue(result.toString(), InitializePaymentResponse.class);
//            String json = EntityUtils.toString(response.getEntity());
//            initializePaymentResponse = gson.fromJson(json, InitializePaymentResponse.class);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return initializePaymentResponse;
    }

    @Override
    @Transactional
    public PaymentVerificationResponse paymentVerification(String reference) throws Exception {
        PaymentVerificationResponse paymentVerificationResponse = null;
        PaymentPaystack paymentPaystack = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);
//
//            if (paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals("false")) {
//                throw new Exception("An error occurred during payment verification");
//            } else if (paymentVerificationResponse.getData().get("status").equals("success")) {
//                Employee employee = employeeRepository.findById(id).orElseThrow(() -> new Exception("Employee not found"));
//                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());
//
//                paymentPaystack = PaymentPaystack.builder()
//                        .user(employee)
//                        .reference(paymentVerificationResponse.getData().getReference())
//                        .amount(paymentVerificationResponse.getData().getAmount())
//                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
//                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
//                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
//                        .channel(paymentVerificationResponse.getData().getChannel())
//                        .currency(paymentVerificationResponse.getData().getCurrency())
//                        .ipAddress(paymentVerificationResponse.getData().getIpAddress())
//                        .pricingPlanType(pricingPlanType)
//                        .createdOn(new Date())
//                        .build();
//            }
        } catch (Exception ex) {
            throw new Exception("Paystack payment verification failed", ex);
        }
//        paystackPaymentRepository.save(paymentPaystack);
        return paymentVerificationResponse;
    }
}
