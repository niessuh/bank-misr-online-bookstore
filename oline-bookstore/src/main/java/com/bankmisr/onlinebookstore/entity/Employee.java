package com.bankmisr.onlinebookstore.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String storeId;
    private String name;
    private String position;
    private double salary;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;
}
