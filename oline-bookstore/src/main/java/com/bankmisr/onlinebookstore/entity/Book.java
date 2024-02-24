package com.bankmisr.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    private String bookName;
    private String title;
    private String author;
    private String publisher;
    private String publicationYear;
    @Indexed(unique = true)
    private String categoryId;
    @Indexed(unique = true)
    private String storeId;
    private double price;
    private double borrowPrice;
    private int quantity;
    private String description;
    private String imageURL;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updateDate;
}
