package com.pranakick.orchid_oasis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BookWithoutPdfDTO {

    private Long id;
    private String title;
    private String author;
    private Set<String> categories;
}
