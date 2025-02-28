package com.pranakick.orchid_oasis.service;

import com.pranakick.orchid_oasis.entity.Book;
import com.pranakick.orchid_oasis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    private final BookRepository bookRepository;

    @Override
    public Book saveBookWithPdf(String title, String author, MultipartFile pdfFile) throws IOException {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPdfFile(pdfFile.getBytes()); //Convert MultipartFile to byte[]
        return bookRepository.save(book);
    }

    @Override
    public byte[] getPdfByBookId(Long bookId){
        return bookRepository.findById(bookId)
                .map(Book::getPdfFile)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
