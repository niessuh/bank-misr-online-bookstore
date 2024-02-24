package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.dto.BorrowedProcessDto;
import com.bankmisr.onlinebookstore.dto.BorrowerDTO;
import com.bankmisr.onlinebookstore.entity.Book;
import com.bankmisr.onlinebookstore.entity.Borrower;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.model.BorrowPeriod;
import com.bankmisr.onlinebookstore.model.BorrowedBook;
import com.bankmisr.onlinebookstore.repository.BorrowerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowerServiceImpl implements BorrowerService {
   private final UserService userService;
   private final BorrowerRepository borrowerRepository;
   private final BookService bookService;
   private final InventoryStoreService inventoryStoreService;
   private final PayManagementService payManagementService;

    public BorrowerServiceImpl(UserService userService, BorrowerRepository borrowerRepository, BookService bookService, InventoryStoreService inventoryStoreService, PayManagementService payManagementService) {
        this.userService = userService;
        this.borrowerRepository = borrowerRepository;
        this.bookService = bookService;
        this.inventoryStoreService = inventoryStoreService;
        this.payManagementService = payManagementService;
    }

    @Override
    public void saveBorrower(BorrowerDTO dto) throws NotFoundException {
        checkIfBorrowerExistAsUser(dto.getName());
        Borrower borrower=new Borrower();
        BeanUtils.copyProperties(dto,borrower);
        borrower.setCreationDate(new Date());
        borrowerRepository.save(borrower);
    }

    @Override
    public void updateBorrower(String borrowerId, BorrowerDTO dto) throws NotFoundException {
        checkIfBorrowerExistAsUser(dto.getName());
        Borrower borrower=new Borrower();
        BeanUtils.copyProperties(dto,borrower);
        borrower.setUpdateDate(new Date());
        borrowerRepository.save(borrower);
    }

    @Override
    public void borrowingBook(BorrowedProcessDto dto) throws BadRequestException {
        Optional<Borrower> borrowerOpt=borrowerRepository.findById(dto.getBorrowerId());
        if(borrowerOpt.isPresent()){
          Optional<Book> bookOptional = bookService.getByStoreIdAndBookId(dto.getStoreId(), dto.getBookId());
          if(bookOptional.isPresent()){
              int availableQuantity=inventoryStoreService.getAvailableNumberOfThisBook(dto.getBookId(), dto.getStoreId());
              if(availableQuantity > 0 ){
                  proceedBorrowProcess(dto, bookOptional, borrowerOpt);
              }else {
                  throw new BadRequestException("no available copies from this book in this store, try in an other store");
              }
          }else {
              throw new BadRequestException("book does not exist in this store");
          }
        }else {
            throw new BadRequestException("Borrower does not exist");
        }
    }

    @Override
    public Borrower getBorrowerById(String borrowerId) {
        return borrowerRepository.findById(borrowerId).orElse(null);
    }

    @Override
    public List<Borrower> getBorrowerList() {
        return borrowerRepository.findAll();
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksForThisBorrower(String borrowerId) throws BadRequestException {
        Optional<Borrower> borrowerOpt=borrowerRepository.findById(borrowerId);
        if(borrowerOpt.isPresent()){
            Borrower borrower=borrowerOpt.get();
            return borrower.getBooksBorrowed();
        }else {
            throw  new BadRequestException("This is Not Borrower Yet !");
        }
    }

    private void proceedBorrowProcess(BorrowedProcessDto dto, Optional<Book> bookOptional, Optional<Borrower> borrowerOpt) throws BadRequestException {
        BorrowedBook borrowedBook=new BorrowedBook();
        borrowedBook.setBookBorrowed(bookOptional.get());
        BorrowPeriod period=getPeriod(dto.getBorrowPeriodInDays());
        borrowedBook.setPeriod(period);
        Borrower borrower= borrowerOpt.get();
        List<BorrowedBook> newBorrowedBooks= borrower.getBooksBorrowed() != null ?  borrower.getBooksBorrowed() : new ArrayList<>();
        newBorrowedBooks.add(borrowedBook);
        borrower.setBooksBorrowed(newBorrowedBooks);
        if(payManagementService.pay()){
            borrowerRepository.save(borrower);
        }else {
            throw new BadRequestException("there are some issue accord on payment process , please try again");
        }
    }

    private BorrowPeriod getPeriod(int borrowPeriodInDays) {
        BorrowPeriod borrowPeriod=new BorrowPeriod();
        borrowPeriod.setBorrowingStartingDate(LocalDateTime.now());
        borrowPeriod.setBorrowingEndDate(LocalDateTime.now().plusDays(borrowPeriodInDays));

        return borrowPeriod;
    }

    private void checkIfBorrowerExistAsUser(String name) throws NotFoundException {
        userService.findByUsername(name);
    }
}
