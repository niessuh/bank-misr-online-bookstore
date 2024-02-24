package com.bankmisr.onlinebookstore.controller;

import com.bankmisr.onlinebookstore.dto.BookDTO;
import com.bankmisr.onlinebookstore.entity.Book;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = { "Book Crud Operations APIs" })
@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/book-store/v1/api")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "This API is used to create new Book and persist it to the database.")
    @PostMapping(value = "/book")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Object> addNewBook(@RequestBody @Valid BookDTO dto) throws BadRequestException {
        bookService.saveBook(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "This API is used to update book.")
    @PutMapping(value = "/book/{bookId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Object> updateBook(@PathVariable String bookId ,@RequestBody @Valid BookDTO dto) throws BadRequestException {
        bookService.updateBook(bookId,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to delete book.")
    @DeleteMapping(value = "/book/{bookId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Object> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get book by its store.")
    @GetMapping(value = "/book/{storeId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Book>> getBooksFromItsStore(@PathVariable String storeId) {
        return new ResponseEntity<>(bookService.getAllBookByStoreId(storeId),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get book by its store and category.")
    @GetMapping(value = "/book/{storeId}/{categoryId}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Book>> getBooksFromByCategory(@PathVariable String storeId,@PathVariable String categoryId) {
        return new ResponseEntity<>(bookService.getAllBookByStoreIdAndCategoryId(storeId,categoryId),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get book by its store and name.")
    @GetMapping(value = "/book/{storeId}/{bookName}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Book> getBookFromItsStoreByName(@PathVariable String storeId,@PathVariable String bookName) {
        return new ResponseEntity<>(bookService.getByStoreIdAndBookName(storeId,bookName),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get book by its store and name.")
    @GetMapping(value = "/book/{storeId}/{author}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Book>> getBooksFromItsStoreByAuthor(@PathVariable String storeId,@PathVariable String author) {
        return new ResponseEntity<>(bookService.getAllBookByStoreIdAndAuthorName(storeId,author),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get book by its store and publisher.")
    @GetMapping(value = "/book/{storeId}/{publisherName}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Book>> getBooksFromItsStoreByPublisher(@PathVariable String storeId,@PathVariable String publisherName) {
        return new ResponseEntity<>(bookService.getAllBookByStoreIdAndPublisherName(storeId,publisherName),HttpStatus.OK);
    }

    @ApiOperation(value = "This API is used to get book by its store and publication year.")
    @GetMapping(value = "/book/{storeId}/{publicationYear}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Book>> getBooksFromItsStoreByPublicationYear(@PathVariable String storeId,@PathVariable String publicationYear) {
        return new ResponseEntity<>(bookService.getAllBookByStoreIdAndPublicationYear(storeId,publicationYear),HttpStatus.OK);
    }
}
