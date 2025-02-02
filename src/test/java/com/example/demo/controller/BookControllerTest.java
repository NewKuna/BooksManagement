package com.example.demo.controller;

import com.example.demo.model.BookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBookListCaseSuccess() throws Exception {
        mockMvc.perform(get("/books")
                        .param("author", "")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("2000"))
                .andExpect(jsonPath("$.status.message").value("Success"));
    }

    @Test
    void createBookCaseSuccess() throws Exception {
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title("Title Name")
                .author("Author Name")
                .publishedDate(LocalDate.of(2568, 2, 1))
                .build();

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code").value("2000"))
                .andExpect(jsonPath("$.status.message").value("Success"));
    }

    @Test
    void createBookCaseFailEmptyTitleOrAuthor() throws Exception {
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title("")
                .author("Author Name")
                .publishedDate(LocalDate.of(2568, 2, 1))
                .build();

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status.code").value("4001"))
                .andExpect(jsonPath("$.status.message").value("Invalid request"))
                .andExpect(jsonPath("$.status.description").value("request parameter title is required"));
    }

    @Test
    void createBookCaseFailInvalidDate() throws Exception {
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .title("Title Name")
                .author("Author Name")
                .publishedDate(LocalDate.of(2569, 1, 1))
                .build();

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status.code").value("4001"))
                .andExpect(jsonPath("$.status.message").value("Invalid request"))
                .andExpect(jsonPath("$.status.description").value("request parameter publishedDate is invalid"));
    }

}