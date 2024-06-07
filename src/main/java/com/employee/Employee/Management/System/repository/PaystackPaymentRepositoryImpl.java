package com.employee.Employee.Management.System.repository;

import com.employee.Employee.Management.System.entity.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaystackPaymentRepositoryImpl extends JpaRepository<PaymentPaystack, Long> {
}