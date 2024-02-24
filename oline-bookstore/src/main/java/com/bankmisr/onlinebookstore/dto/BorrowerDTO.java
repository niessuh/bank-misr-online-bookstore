package com.bankmisr.onlinebookstore.dto;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import com.bankmisr.onlinebookstore.entity.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Setter
@Getter
public class BorrowerDTO {
    @NotBlank(message = AppConstants.INVALID_FIELD + "name")
    private String name;
    @NotBlank(message = AppConstants.INVALID_FIELD + "nationalId")
    private String nationalId;
    @NotBlank(message = AppConstants.INVALID_FIELD + "email")
    private String email;
    @NotBlank(message = AppConstants.INVALID_FIELD + "address")
    private String address;
    @NotBlank(message = AppConstants.INVALID_FIELD + "phoneNumber")
    private String phoneNumber;
    @Size(min = 1 , message = AppConstants.INVALID_FIELD + "borrowingDays")
    private int borrowingDays;
}
