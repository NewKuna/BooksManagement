package com.example.demo.service;

import com.example.demo.common.exception.CommonException;
import com.example.demo.model.BookRequestDto;
import com.example.demo.model.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponseDto> getBookList(String author, Pageable pageable) throws CommonException;

    BookResponseDto createBook(BookRequestDto bookRequestDto) throws CommonException;

}
