package com.employee.Employee.Management.System.entity;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    // instance variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String secondName;
    @Column(name = "email_id", nullable = false, unique = true)
    private String email;
    @Column(name = "address")
    private String employeeAddress;

    @Column(name = "phone")
    private String employeePhone;
//
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_on", updatable = false, nullable = false)
//    private Date createdOn;

}
