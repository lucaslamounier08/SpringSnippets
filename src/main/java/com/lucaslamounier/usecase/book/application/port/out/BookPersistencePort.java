package com.lucaslamounier.usecase.book.application.port.out;

import com.lucaslamounier.usecase.book.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookPersistencePort {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    void deleteById(Long id);
}
