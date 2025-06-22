package com.pranakick.orchid_oasis.controller;

import com.pranakick.orchid_oasis.dto.BookWithoutPdfDTO;
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
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * This method is used to upload a book with a PDF file.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param file The PDF file of the book.
     * @return The saved book.
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Book> uploadBookWithPdf(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("category") List<String> categoryName,
            @RequestParam("file") MultipartFile file) {

        try{
            Book savedBook = bookService.saveBookWithPdf(title,author,categoryName, file );
            return ResponseEntity.ok(savedBook);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method is used to get a PDF file of a book by its ID.
     * @param id The ID of the book.
     * @return The PDF file of the book.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id){
        byte[] pdfData = bookService.getPdfByBookId(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=book.pdf")
                .body(pdfData);
    }

    /**
     * This method is used to delete a book by its ID.
     * @param bookId The ID of the book.
     * @return A message indicating the success of the operation.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable(name = "id")Long bookId){
        String message = bookService.deleteBook(bookId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * This method is used to get all books without PDF.
     * @return A list of all books without PDF.
     */
    @GetMapping("/get-all-books")
    public List<BookWithoutPdfDTO> getAllBooksWithoutPdf(){
        return bookService.getAllBooks();
    }
}
