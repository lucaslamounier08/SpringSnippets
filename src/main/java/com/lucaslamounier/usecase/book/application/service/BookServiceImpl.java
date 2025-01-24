package com.lucaslamounier.usecase.book.application.service;

import com.lucaslamounier.usecase.book.application.port.in.BookService;
import com.lucaslamounier.usecase.book.application.port.out.BookPersistencePort;
import com.lucaslamounier.usecase.book.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
