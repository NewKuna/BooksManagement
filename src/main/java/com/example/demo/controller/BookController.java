package com.example.demo.controller;

import com.example.demo.common.exception.CommonException;
import com.example.demo.common.model.Status;
import com.example.demo.common.model.dto.PagingDto;
import com.example.demo.common.model.dto.ResponseDto;
import com.example.demo.common.model.dto.ResponsePagingDto;
import com.example.demo.model.BookRequestDto;
import com.example.demo.model.BookResponseDto;
import com.example.demo.service.BookService;
import com.example.demo.service.ValidateService;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    private ValidateService validateService;

    @Autowired
    public void setValidateService(ValidateService validateService) {
        this.validateService = validateService;
    }


    @GetMapping
    public ResponseEntity<ResponsePagingDto<BookResponseDto>> getBookList(
            @RequestParam(required = false) String author,
            @ParameterObject Pageable pageable
    ) throws CommonException {

        Page<BookResponseDto> response = bookService.getBookList(author, pageable);

        return ResponseEntity.ok()
                .body(ResponsePagingDto.<BookResponseDto>builder()
                        .status(Status.SUCCESS.getStatusDTO())
                        .data(response.getContent())
                        .paging(PagingDto.builder()
                                .itemCount(response.getSize())
                                .totalItems(response.getTotalElements())
                                .totalPages(response.getTotalPages())
                                .currentPage(response.getNumber())
                                .build())
                        .build());
    }

    @PostMapping
    public ResponseEntity<ResponseDto<BookResponseDto>> createBook(
            @RequestBody BookRequestDto bookRequestDto
    ) throws CommonException {

        validateService.validateBook(bookRequestDto);
        BookResponseDto response = bookService.createBook(bookRequestDto);

        return ResponseEntity.ok()
                .body(ResponseDto.<BookResponseDto>builder()
                        .status(Status.SUCCESS.getStatusDTO())
                        .data(response)
                        .build());
    }

}
