package com.example.demo.service;

import com.example.demo.common.exception.CommonException;
import com.example.demo.entity.BookEntity;
import com.example.demo.model.*;
import com.example.demo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    @Cacheable(value = "booksByAuthor", key = "#author != null ? #author : 'all'")
    public Page<BookResponseDto> getBookList(String author, Pageable pageable) throws CommonException {
        Page<BookEntity> books;

        if (StringUtils.isEmpty(author)) {
            books = bookRepository.findAll(pageable);
        } else {
            books = bookRepository.findAllByAuthor(author, pageable);
        }

        List<BookResponseDto> result = books.stream()
                .map(this::bookResponseDtoMapper)
                .toList();

        return new PageImpl<>(result, books.getPageable(), books.getTotalElements());
    }

    @Override
    public BookResponseDto createBook(BookRequestDto bookRequestDto) throws CommonException {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookRequestDto.getTitle());
        bookEntity.setAuthor(bookRequestDto.getAuthor());
        bookEntity.setPublishedDate(bookRequestDto.getPublishedDate());

        BookEntity result = bookRepository.save(bookEntity);
        return this.bookResponseDtoMapper(result);
    }

    private BookResponseDto bookResponseDtoMapper(BookEntity bookEntity) {
        return BookResponseDto.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .publishedDate(bookEntity.getPublishedDate())
                .build();
    }

}
