package com.bankmisr.onlinebookstore.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@Document(collection = "category")
public class Category {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String storeId;
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;
}
