package com.example.registrationLogin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "student", uniqueConstraints = @UniqueConstraint(columnNames = "entranceRollNo"))
//uniqueconstraints helps to make entranceRollNo as unique for all students
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roll_no", nullable = false)
    private long rollNo;
    private int entranceRollNo;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date DOB;
    private String address;
    private float entranceScore;
    private String identityNo;
    @Column(unique = true)
    private String loggedInEmail;
    private String assignedMessage;

}
