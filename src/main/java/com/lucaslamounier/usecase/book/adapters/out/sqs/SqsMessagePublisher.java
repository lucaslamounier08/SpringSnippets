package com.lucaslamounier.usecase.book.adapters.out.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucaslamounier.usecase.book.core.domain.Book;
import com.lucaslamounier.usecase.shared.annotation.LogExecutionTime;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SqsMessagePublisher {

    private final SqsTemplate sqsTemplate;
    private final AwsSqsProperties awsSqsProperties;
    private final ObjectMapper objectMapper;

    @LogExecutionTime
    public void publish(Book book) {
        try {
            String payload = objectMapper.writeValueAsString(book);
            sqsTemplate.send(awsSqsProperties.getQueueName(), payload);

            log.info("Message published: {}", book);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error to converter message: " + book, e);
        }
    }
}
