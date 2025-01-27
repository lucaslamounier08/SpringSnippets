package com.lucaslamounier.usecase.book.adapters.out.persistence;

import com.lucaslamounier.usecase.book.ports.out.BookPersistencePort;
import com.lucaslamounier.usecase.book.core.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements BookPersistencePort {

    private final BookRepository bookRepository;
    @PersistenceContext
    private final EntityManager entityManager;

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

    @Override
    public List<Book> findBooksByCriteria(String title, String author, String isbn,
                                          LocalDate publishedDateMin, LocalDate publishedDateMax,
                                          BigDecimal priceMin, BigDecimal priceMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);
        Root<BookEntity> root = query.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (author != null && !author.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
        }

        if (isbn != null && !isbn.isBlank()) {
            predicates.add(cb.equal(root.get("isbn"), isbn));
        }

        if (publishedDateMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("publishedDate"), publishedDateMin));
        }

        if (publishedDateMax != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("publishedDate"), publishedDateMax));
        }

        if (priceMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), priceMin));
        }

        if (priceMax != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), priceMax));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList()
                .stream()
                .map(this::toDomain)
                .toList();
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
