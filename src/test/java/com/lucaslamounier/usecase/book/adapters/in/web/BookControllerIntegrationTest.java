package com.lucaslamounier.usecase.book.adapters.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("The Catcher in the Rye"))
                .andExpect(jsonPath("$.author").value("J.D. Salinger"));
    }

    @Test
    void testGetBookById() throws Exception {
        String bookJson = """
                {
                  "title": "1984",
                  "author": "George Orwell",
                  "publishedDate": "1949-06-08",
                  "isbn": "9780451524935",
                  "price": 8.99
                }
                """;

        String response = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long bookId = extractIdFromResponse(response);

        // Retrieve the book by ID
        mockMvc.perform(get("/api/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value("1984"))
                .andExpect(jsonPath("$.author").value("George Orwell"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        // Ensure the database has at least one book
        testCreateBook();

        mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber());
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

        String response = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long bookId = extractIdFromResponse(response);

        // Delete the book
        mockMvc.perform(delete("/api/books/" + bookId))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Verify the book no longer exists
        mockMvc.perform(get("/api/books/" + bookId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Helper method to extract the ID from a JSON response
    private Long extractIdFromResponse(String response) {
        try {
            return Long.valueOf(response.substring(response.indexOf("\"id\":") + 5, response.indexOf(",", response.indexOf("\"id\":"))).trim());
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract ID from response", e);
        }
    }
}
