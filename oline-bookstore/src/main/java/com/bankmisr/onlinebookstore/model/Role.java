package com.bankmisr.onlinebookstore.model;

import com.bankmisr.onlinebookstore.constant.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Setter
@Getter
public class Role {
	@Id
	private String id;
	private RoleEnum name;

}
