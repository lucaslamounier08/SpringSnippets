package com.lucaslamounier.usecase.book.adapters.out.sqs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class AwsSqsProperties {

    @Value("${app.sqs.queue-name}")
    private String queueName;
}
