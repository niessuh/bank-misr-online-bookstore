package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.dto.BorrowedProcessDto;
import com.bankmisr.onlinebookstore.dto.BorrowerDTO;
import com.bankmisr.onlinebookstore.entity.Borrower;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.model.BorrowedBook;

import java.util.List;

public interface BorrowerService{
    void saveBorrower(BorrowerDTO dto) throws NotFoundException;
    void updateBorrower(String borrowerId,BorrowerDTO dto) throws NotFoundException;
    void borrowingBook(BorrowedProcessDto dto) throws BadRequestException;
    Borrower getBorrowerById(String borrowerId);
    List<Borrower> getBorrowerList();
    List<BorrowedBook> getBorrowedBooksForThisBorrower(String borrowerId) throws BadRequestException;
}
