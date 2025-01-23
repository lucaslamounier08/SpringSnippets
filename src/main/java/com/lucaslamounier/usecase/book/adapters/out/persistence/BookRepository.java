package com.lucaslamounier.usecase.book.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}