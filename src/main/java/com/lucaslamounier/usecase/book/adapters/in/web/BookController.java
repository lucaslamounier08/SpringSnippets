package com.lucaslamounier.usecase.book.adapters.in.web;

import com.lucaslamounier.usecase.book.core.domain.Book;
import com.lucaslamounier.usecase.book.ports.in.BookService;
import com.lucaslamounier.usecase.shared.annotation.LogExecutionTime;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @LogExecutionTime
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        book = bookService.createBook(book);
        return ResponseEntity.created(URI.create("/api/books/" + book.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Optional<Book> existingBook = bookService.getBookById(id);

        if (existingBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book updatedBook = bookService.updateBook(existingBook.get(), book);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
