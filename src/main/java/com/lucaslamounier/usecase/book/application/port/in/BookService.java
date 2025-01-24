package com.lucaslamounier.usecase.book.application.port.in;

import com.lucaslamounier.usecase.book.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(Book book);
    Optional<Book> getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Book existingBook, Book book);
    void deleteBook(Long id);
}
