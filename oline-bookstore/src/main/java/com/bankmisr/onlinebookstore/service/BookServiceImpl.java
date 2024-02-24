package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.dto.BookDTO;
import com.bankmisr.onlinebookstore.entity.Book;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBook(BookDTO dto) throws BadRequestException {
        Optional<Book> bookOptional = checkIfBookAlreadyExistInThisStore(dto);
        if(bookOptional.isPresent()){
            throw new BadRequestException("Book already exist in this store with the same category");
        }
        Book newBook=new Book();
        BeanUtils.copyProperties(dto,newBook);
        newBook.setCreationDate(new Date());
        bookRepository.save(newBook);

    }
    @Override
    public void updateBook(String bookId, BookDTO dto) throws BadRequestException {
        Optional<Book> bookOptional = checkIfBookAlreadyExistInThisStore(dto);
        if(! bookOptional.isPresent()){
            throw new BadRequestException("Book not exist in this store with the same category");
        }
        Book updatedBook=bookOptional.get();
        BeanUtils.copyProperties(dto,updatedBook);
        updatedBook.setUpdateDate(new Date());
        bookRepository.save(updatedBook);

    }

    @Override
    public void deleteBook(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllBookByStoreId(String storeId) {
        return bookRepository.findByStoreId(storeId);
    }

    @Override
    public List<Book> getAllBookByStoreIdAndCategoryId(String storeId, String categoryId) {
        return bookRepository.findByStoreIdAndCategoryId(storeId,categoryId);
    }

    @Override
    public Book getByStoreIdAndBookName(String storeId, String bookName) {
        return bookRepository.findByStoreIdAndBookName(storeId, bookName);
    }

    @Override
    public Optional<Book> getByStoreIdAndBookId(String storeId, String bookId) {
        return bookRepository.findByStoreIdAndId(storeId, bookId);
    }


    @Override
    public List<Book> getAllBookByStoreIdAndAuthorName(String storeId, String authorName) {
        return bookRepository.findByStoreIdAndAuthor(storeId,authorName);
    }

    @Override
    public List<Book> getAllBookByStoreIdAndPublisherName(String storeId, String publisherName) {
        return bookRepository.findByStoreIdAndPublisher(storeId,publisherName);
    }

    @Override
    public List<Book> getAllBookByStoreIdAndPublicationYear(String storeId, String publicationYear) {
        return bookRepository.findByStoreIdAndPublicationYear(storeId,publicationYear);
    }


    private  Optional<Book> checkIfBookAlreadyExistInThisStore(BookDTO dto){
        return bookRepository.findByBookNameAndCategoryIdAndStoreId(dto.getBookName(), dto.getCategoryId(), dto.getStoreId());
    }
}
