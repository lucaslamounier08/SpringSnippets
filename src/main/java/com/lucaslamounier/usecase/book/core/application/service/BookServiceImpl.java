package com.lucaslamounier.usecase.book.core.application.service;

import com.lucaslamounier.usecase.book.ports.in.BookService;
import com.lucaslamounier.usecase.book.ports.out.BookPersistencePort;
import com.lucaslamounier.usecase.book.core.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookPersistencePort bookPersistencePort;

    @Override
    public Book createBook(Book book) {
        return bookPersistencePort.save(book);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookPersistencePort.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookPersistencePort.findAll();
    }

    @Override
    public Book updateBook(Book existingBook, Book book) {
        return bookPersistencePort.updateBook(existingBook, book);
    }

    @Override
    public void deleteBook(Long id) {
        bookPersistencePort.deleteById(id);
    }

    @Override
    public List<Book> findBooksByCriteria(String title, String author, String isbn, LocalDate publishedDateMin, LocalDate publishedDateMax, BigDecimal priceMin, BigDecimal priceMax) {
        return bookPersistencePort.findBooksByCriteria(title, author, isbn, publishedDateMin, publishedDateMax, priceMin, priceMax);
    }
}
