package com.lucaslamounier.usecase.book.adapters.out.sqs;

import com.lucaslamounier.usecase.book.adapters.out.persistence.BookEntity;
import com.lucaslamounier.usecase.book.adapters.out.persistence.BookRepository;
import com.lucaslamounier.usecase.book.core.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class SqsMessagePublisherIntegrationTest {

    private static final String QUEUE_NAME = "crete-book-queue";

    @Autowired
    private SqsMessagePublisher sqsMessagePublisher;

    @Autowired
    private BookRepository bookRepository;

    private static LocalStackContainer localStack;

    @BeforeAll
    static void setupSpec() throws Exception {
        localStack = new LocalStackContainer(
                DockerImageName.parse("localstack/localstack:3.4.0"))
                .withServices(LocalStackContainer.Service.SQS);
        localStack.start();
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", QUEUE_NAME);
    }

    @DynamicPropertySource
    static void setUpAwsSqsProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.aws.sqs.endpoint", () -> localStack.getEndpointOverride(LocalStackContainer.Service.SQS).toString());
        registry.add("spring.cloud.aws.credentials.access-key", localStack::getAccessKey);
        registry.add("spring.cloud.aws.credentials.secret-key", localStack::getSecretKey);
        registry.add("spring.cloud.aws.region.static", localStack::getRegion);
        registry.add("app.sqs.queue-name", () -> QUEUE_NAME);
    }

    @AfterAll
    static void cleanupSpec() {
        if (localStack != null && localStack.isRunning()) {
            localStack.stop();
        }
    }

    @Test
    void givenBook_whenPublishEvent_mustSaveNewBook() throws InterruptedException {
        // Given
        Book book = Book.builder()
                .title("titlePublishedTest")
                .author("authorTest")
                .build();

        // When
        sqsMessagePublisher.publish(book);

        // Pause to ensure consumer processes the message
        Thread.sleep(2000);

        // Then: the book must be saved
        Optional<BookEntity> bookPersisted = bookRepository.findByTitle("titlePublishedTest");
        Assertions.assertThat(bookPersisted).isPresent();
        Assertions.assertThat(bookPersisted.get().getId()).isNotNull();
        Assertions.assertThat(bookPersisted.get().getTitle()).isEqualTo("titlePublishedTest");
        Assertions.assertThat(bookPersisted.get().getAuthor()).isEqualTo("authorTest");
    }
}