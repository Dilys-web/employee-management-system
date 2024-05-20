package com.employee.Employee.Management.System.config;

import com.employee.Employee.Management.System.exception.CustomHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Bean

    public HandlerExceptionResolver customHandlerExceptionResolver() {
        return new CustomHandlerExceptionResolver();
    }
}
