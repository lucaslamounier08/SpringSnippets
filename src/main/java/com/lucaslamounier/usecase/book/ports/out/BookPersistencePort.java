package com.lucaslamounier.usecase.book.ports.out;

import com.lucaslamounier.usecase.book.core.domain.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookPersistencePort {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Book updateBook(Book existingBook, Book book);
    void deleteById(Long id);
    List<Book> findBooksByCriteria(String title, String author, String isbn, LocalDate publishedDateMin, LocalDate publishedDateMax, BigDecimal priceMin, BigDecimal priceMax);
}
