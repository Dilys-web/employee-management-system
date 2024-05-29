package com.employee.Employee.Management.System.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException() {
        super("User not found");
    }
}
