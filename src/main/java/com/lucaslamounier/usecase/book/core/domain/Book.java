package com.lucaslamounier.usecase.book.core.domain;

import com.lucaslamounier.usecase.book.adapters.in.web.annotations.ValidPrice;
import com.lucaslamounier.usecase.book.adapters.in.web.annotations.ValidPublishedDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Book {
    private Long id;
    @NotBlank @Size(max = 255) private String title;
    @NotBlank @Size(max = 255) private String author;
    @ValidPublishedDate private LocalDate publishedDate;
    @Size(max = 20) private String isbn;
    @ValidPrice private BigDecimal price;
}
