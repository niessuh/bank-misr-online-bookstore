package com.bankmisr.onlinebookstore.entity;

import com.bankmisr.onlinebookstore.model.BorrowedBook;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Document(collection = "borrower")
public class Borrower {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String nationalId;
    @Indexed(unique = true)
    private String email;
    private String address;
    private String phoneNumber;
    private List<BorrowedBook> booksBorrowed;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updateDate;
}
