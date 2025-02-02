package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookRequestDto {

    @JsonProperty("title")
    @NotNull
    private String title;

    @JsonProperty("author")
    @NotNull
    private String author;

    @JsonProperty("publishedDate")
    @NotNull
    @Schema(type = "string", example = "2568-02-01")
    private LocalDate publishedDate;

}
