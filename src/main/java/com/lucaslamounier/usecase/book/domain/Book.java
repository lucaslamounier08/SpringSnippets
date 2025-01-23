package com.lucaslamounier.usecase.book.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Book {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private String isbn;
    private BigDecimal price;
}
