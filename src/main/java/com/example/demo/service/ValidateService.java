package com.example.demo.service;

import com.example.demo.common.exception.CommonException;
import com.example.demo.common.model.Status;
import com.example.demo.model.BookRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class ValidateService {

    public void validateBook(BookRequestDto bookRequestDto) throws CommonException {
        LocalDate minimumDate = LocalDate.of(1001, 1, 1);
        LocalDate maximumDate = LocalDate.now().plusYears(543);

        if (StringUtils.isEmpty(bookRequestDto.getTitle())) {
            throw new CommonException(Status.INVALID_REQUEST, "request parameter title is required");
        }
        if (StringUtils.isEmpty(bookRequestDto.getAuthor())) {
            throw new CommonException(Status.INVALID_REQUEST, "request parameter author is required");
        }
        if (bookRequestDto.getPublishedDate() == null) {
            throw new CommonException(Status.INVALID_REQUEST, "request parameter publishedDate is required");
        }
        if (bookRequestDto.getPublishedDate().isBefore(minimumDate)
            || bookRequestDto.getPublishedDate().isAfter(maximumDate)) {
            throw new CommonException(Status.INVALID_REQUEST, "request parameter publishedDate is invalid");
        }
    }

}
