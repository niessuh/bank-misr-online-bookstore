package com.bankmisr.onlinebookstore.dto;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class BorrowedProcessDto {
    @NotBlank(message = AppConstants.INVALID_FIELD + "borrowerId")
    private String borrowerId;
    @NotBlank(message = AppConstants.INVALID_FIELD + "bookId")
    private String bookId;
    @NotBlank(message = AppConstants.INVALID_FIELD + "storeId")
    private String storeId;
    @NotNull(message = AppConstants.INVALID_FIELD + "borrowPeriodInDays")
    private Integer borrowPeriodInDays;

}
