package com.bankmisr.onlinebookstore.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Document(collection = "store")
public class Store {
    @Id
    private String id;
    private String name;
    private String location;
    private List<Book> books;
    private List<Category> categories;
    private List<Employee> employees;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;
}
