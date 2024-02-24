package com.bankmisr.onlinebookstore.entity;

import com.bankmisr.onlinebookstore.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Document(collection = "user")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Indexed(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private boolean active;
    private List<Role> roles;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;
}
