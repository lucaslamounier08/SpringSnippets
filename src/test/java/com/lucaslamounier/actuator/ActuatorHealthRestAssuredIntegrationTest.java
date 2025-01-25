package com.lucaslamounier.actuator;

import com.lucaslamounier.usecase.book.adapters.in.sqs.SqsMessageConsumer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActuatorHealthRestAssuredIntegrationTest {

    @LocalServerPort
    private int port;

    @MockitoBean
    private SqsMessageConsumer sqsMessageConsumer;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void healthEndpointShouldReturnUp() {
        RestAssured
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
}
