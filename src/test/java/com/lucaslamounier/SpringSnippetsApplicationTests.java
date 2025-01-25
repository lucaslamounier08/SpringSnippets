package com.lucaslamounier;

import com.lucaslamounier.usecase.book.adapters.in.sqs.SqsMessageConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class SpringSnippetsApplicationTests {

	@MockitoBean
	private SqsMessageConsumer sqsMessageConsumer;

	@Test
	void contextLoads() {
	}

}
