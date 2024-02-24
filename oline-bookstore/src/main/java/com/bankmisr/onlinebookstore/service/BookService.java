package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.dto.BookDTO;
import com.bankmisr.onlinebookstore.entity.Book;
import com.bankmisr.onlinebookstore.exception.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void saveBook(BookDTO dto) throws BadRequestException;
    void updateBook(String bookId,BookDTO dto) throws BadRequestException;
    void deleteBook(String bookId);
    List<Book> getAllBook();
    List<Book> getAllBookByStoreId(String storeId);
    List<Book> getAllBookByStoreIdAndCategoryId(String storeId , String categoryId);
    Book getByStoreIdAndBookName(String storeId ,String bookName);

    Optional<Book> getByStoreIdAndBookId(String storeId, String bookId);

    List<Book> getAllBookByStoreIdAndAuthorName(String storeId, String authorName);
    List<Book> getAllBookByStoreIdAndPublisherName(String storeId, String publisherName);
    List<Book> getAllBookByStoreIdAndPublicationYear(String storeId, String publicationYear);

}
