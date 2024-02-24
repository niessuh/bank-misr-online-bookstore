package com.bankmisr.onlinebookstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bankmisr.onlinebookstore.constant.AppConstants;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class BorrowPeriod {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.BORROW_TIMESTAMP_FORMAT)
    private LocalDateTime borrowingStartingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.BORROW_TIMESTAMP_FORMAT)
    private LocalDateTime borrowingEndDate;
}
