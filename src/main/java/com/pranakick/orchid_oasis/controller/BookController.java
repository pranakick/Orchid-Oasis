package com.pranakick.orchid_oasis.controller;

import com.pranakick.orchid_oasis.entity.Book;
import com.pranakick.orchid_oasis.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Book> uploadBookWithPdf(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("file")MultipartFile file){
        try{
            Book savedBook = bookService.saveBookWithPdf(title,author,file);
            return ResponseEntity.ok(savedBook);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id){
        byte[] pdfData = bookService.getPdfByBookId(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=book.pdf")
                .body(pdfData);
    }
}
