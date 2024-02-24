package com.bankmisr.onlinebookstore.model;

import com.bankmisr.onlinebookstore.entity.Book;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowedBook {
    private Book bookBorrowed;
    private BorrowPeriod period;
}
