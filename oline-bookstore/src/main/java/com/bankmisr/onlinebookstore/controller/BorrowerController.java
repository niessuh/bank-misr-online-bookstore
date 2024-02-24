package com.bankmisr.onlinebookstore.controller;

import com.bankmisr.onlinebookstore.dto.BorrowedProcessDto;
import com.bankmisr.onlinebookstore.dto.BorrowerDTO;
import com.bankmisr.onlinebookstore.entity.Borrower;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.model.BorrowedBook;
import com.bankmisr.onlinebookstore.service.BorrowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = { "Borrower Operations APIs" })
@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/book-store/v1/api")
public class BorrowerController {
    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @ApiOperation(value = "This API is used to create new borrower and persist it to the database.")
    @PostMapping(value = "/borrower")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Object> addNewBorrower(@RequestBody @Valid BorrowerDTO dto) throws NotFoundException {
        borrowerService.saveBorrower(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "This API is used to update borrower.")
    @PutMapping(value = "/borrower/{borrowerId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Object> updateBorrower(@PathVariable String borrowerId ,@RequestBody @Valid BorrowerDTO dto) throws NotFoundException {
        borrowerService.updateBorrower(borrowerId,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get borrower by Id.")
    @GetMapping(value = "/borrower/{borrowerId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable String borrowerId){
        return new ResponseEntity<>(borrowerService.getBorrowerById(borrowerId),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get all borrowers.")
    @GetMapping(value = "/borrower")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<Borrower>> getAllBorrowers(){
        return new ResponseEntity<>(borrowerService.getBorrowerList(),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to borrowing Book.")
    @PostMapping(value = "/borrower/borrowedBook")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Object> doBorrowedBook(@RequestBody @Valid BorrowedProcessDto dto) throws BadRequestException {
        borrowerService.borrowingBook(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get borrower by Id.")
    @GetMapping(value = "/borrower/books-details/{borrowerId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<BorrowedBook>> getBorrowersBooks(@PathVariable String borrowerId) throws BadRequestException {
        return new ResponseEntity<>(borrowerService.getBorrowedBooksForThisBorrower(borrowerId),HttpStatus.OK);
    }

}
