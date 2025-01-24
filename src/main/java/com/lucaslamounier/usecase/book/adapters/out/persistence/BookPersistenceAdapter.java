package com.lucaslamounier.usecase.book.adapters.out.persistence;

import com.lucaslamounier.usecase.book.application.port.out.BookPersistencePort;
import com.lucaslamounier.usecase.book.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements BookPersistencePort {

    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        BookEntity entity = toEntity(book);
        return toDomain(bookRepository.save(entity));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Book updateBook(Book existingBook, Book book) {
        BookEntity entity = toEntity(book);
        entity.setId(existingBook.getId());

        return toDomain(bookRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPublishedDate(book.getPublishedDate());
        entity.setIsbn(book.getIsbn());
        entity.setPrice(book.getPrice());
        return entity;
    }

    private Book toDomain(BookEntity entity) {
        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .publishedDate(entity.getPublishedDate())
                .isbn(entity.getIsbn())
                .price(entity.getPrice())
                .build();
    }
}
