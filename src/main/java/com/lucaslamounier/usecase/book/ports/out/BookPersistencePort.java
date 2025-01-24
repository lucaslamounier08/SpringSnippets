package com.lucaslamounier.usecase.book.ports.out;

import com.lucaslamounier.usecase.book.core.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookPersistencePort {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Book updateBook(Book existingBook, Book book);
    void deleteById(Long id);
}
