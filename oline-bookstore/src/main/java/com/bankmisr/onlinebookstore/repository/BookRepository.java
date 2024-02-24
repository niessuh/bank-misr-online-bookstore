package com.bankmisr.onlinebookstore.repository;

import com.bankmisr.onlinebookstore.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String> {
    Optional<Book> findByBookNameAndCategoryIdAndStoreId(String bookName, String categoryId,String storeId);
    List<Book> findByStoreId(String storeId);
    Book findByStoreIdAndBookName(String storeId,String bookName);
    List<Book> findByStoreIdAndCategoryId(String storeId,String categoryId);
    List<Book> findByStoreIdAndAuthor(String storeId,String author);
    List<Book> findByStoreIdAndPublicationYear(String storeId,String publicationYear);
    List<Book> findByStoreIdAndPublisher(String storeId,String publisher);

    Optional<Book> findByStoreIdAndId(String storeId, String bookId);
}
