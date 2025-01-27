package com.lucaslamounier.usecase.book.ports.in;

import com.lucaslamounier.usecase.book.core.domain.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(Book book);
    Optional<Book> getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Book existingBook, Book book);
    void deleteBook(Long id);
    List<Book> findBooksByCriteria(String title, String author, String isbn, LocalDate publishedDateMin, LocalDate publishedDateMax, BigDecimal priceMin, BigDecimal priceMax);
}
