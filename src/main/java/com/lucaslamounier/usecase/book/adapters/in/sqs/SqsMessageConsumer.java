package com.lucaslamounier.usecase.book.adapters.in.sqs;

import com.lucaslamounier.usecase.book.core.domain.Book;
import com.lucaslamounier.usecase.book.ports.in.BookService;
import com.lucaslamounier.usecase.shared.annotation.LogExecutionTime;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SqsMessageConsumer {

    private final BookService bookService;

    @LogExecutionTime
    @SqsListener("${app.sqs.queue-name}")
    public void listenToQueue(Book book) {
        log.info("Received message: {}", book);

        Book bookSaved = bookService.createBook(book);

        log.info("Book saved with id {}", bookSaved.getId());
    }
}