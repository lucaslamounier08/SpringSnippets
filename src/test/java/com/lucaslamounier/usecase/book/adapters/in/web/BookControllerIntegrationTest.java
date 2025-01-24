package com.lucaslamounier.usecase.book.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucaslamounier.usecase.book.core.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBook() throws Exception {
        String bookJson = """
                {
                  "title": "The Catcher in the Rye",
                  "author": "J.D. Salinger",
                  "publishedDate": "1951-07-16",
                  "isbn": "9780316769488",
                  "price": 10.99
                }
                """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/books/\\d+")))
                .andExpect(content().string(""));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidBooks")
    void givenInvalidRequest_whenCreateBook_mustReturnBadRequest(Book invalidBookJson, String expectedField, String expectedMessage) throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookJson)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value(expectedField))
                .andExpect(jsonPath("$.errors[0].message").value(expectedMessage));
    }

    private static Stream<Arguments> provideInvalidBooks() {
        Book validBook = Book.builder()
                .title("titleTest")
                .author("authorTest")
                .publishedDate(LocalDate.now())
                .isbn("isbnTest")
                .price(BigDecimal.TEN).build();

        return Stream.of(
                Arguments.of(validBook.toBuilder().title(null).build(),
                        "title", "must not be blank"
                ),
                Arguments.of(validBook.toBuilder().title("").build(),
                        "title", "must not be blank"
                ),
                Arguments.of(validBook.toBuilder().title("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.").build(),
                        "title", "size must be between 0 and 255"
                ),
                Arguments.of(validBook.toBuilder().author(null).build(),
                        "author", "must not be blank"
                ),
                Arguments.of(validBook.toBuilder().author("").build(),
                        "author", "must not be blank"
                ),
                Arguments.of(validBook.toBuilder().author("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.").build(),
                        "author", "size must be between 0 and 255"
                ),
                Arguments.of(validBook.toBuilder().publishedDate(LocalDate.MAX).build(),
                        "publishedDate", "Published date year must be between 1500 and 2120"
                ),
                Arguments.of(validBook.toBuilder().publishedDate(LocalDate.MIN).build(),
                        "publishedDate", "Published date year must be between 1500 and 2120"
                ),
                Arguments.of(validBook.toBuilder().isbn("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.").build(),
                        "isbn", "size must be between 0 and 20"
                ),
                Arguments.of(validBook.toBuilder().price(BigDecimal.valueOf(-10L)).build(),
                        "price", "Price must be greater than 0 and less than 999"
                ),
                Arguments.of(validBook.toBuilder().price(BigDecimal.valueOf(1000L)).build(),
                        "price", "Price must be greater than 0 and less than 999"
                ));
    }

    @Test
    void testGetBookById() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(get("/api/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$.publishedDate").value("1925-04-10"))
                .andExpect(jsonPath("$.isbn").value("9780743273565"))
                .andExpect(jsonPath("$.price").value("10.99"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].title").isString())
                .andExpect(jsonPath("$[0].author").isString())
                .andExpect(jsonPath("$[0].publishedDate").isString())
                .andExpect(jsonPath("$[0].isbn").isString())
                .andExpect(jsonPath("$[0].price").isNumber());
    }

    @Test
    void testUpdateBook() throws Exception {
        // First, create a book
        String bookJson = """
                {
                  "title": "Brave New World",
                  "author": "Aldous Huxley",
                  "publishedDate": "1932-08-18",
                  "isbn": "9780060850524",
                  "price": 9.99
                }
                """;

        MockHttpServletResponse response = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/books/\\d+")))
                .andReturn()
                .getResponse();

        long bookId = Long.valueOf(response.getHeader("Location").split("/")[3]);

        // Update the book
        String bookJsonUpdate = """
                {
                  "title": "Brave New World Updated",
                  "author": "Aldous Huxley Updated",
                  "publishedDate": "2024-08-18",
                  "isbn": "9780060850524",
                  "price": 99.99
                }
                """;

        mockMvc.perform(put("/api/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJsonUpdate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("Brave New World Updated"))
                .andExpect(jsonPath("$.author").value("Aldous Huxley Updated"))
                .andExpect(jsonPath("$.publishedDate").value("2024-08-18"))
                .andExpect(jsonPath("$.isbn").value("9780060850524"))
                .andExpect(jsonPath("$.price").value("99.99"));

        // Verify the book is updated
        mockMvc.perform(get("/api/books/" + bookId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("Brave New World Updated"))
                .andExpect(jsonPath("$.author").value("Aldous Huxley Updated"))
                .andExpect(jsonPath("$.publishedDate").value("2024-08-18"))
                .andExpect(jsonPath("$.isbn").value("9780060850524"))
                .andExpect(jsonPath("$.price").value("99.99"));
    }

    @Test
    void testDeleteBook() throws Exception {
        // First, create a book
        String bookJson = """
                {
                  "title": "Brave New World",
                  "author": "Aldous Huxley",
                  "publishedDate": "1932-08-18",
                  "isbn": "9780060850524",
                  "price": 9.99
                }
                """;

        MockHttpServletResponse response = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/books/\\d+")))
                .andReturn()
                .getResponse();

        long bookId = Long.valueOf(response.getHeader("Location").split("/")[3]);

        // Delete the book
        mockMvc.perform(delete("/api/books/" + bookId))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Verify the book no longer exists
        mockMvc.perform(get("/api/books/" + bookId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
