package com.pranakick.orchid_oasis.service;

import com.pranakick.orchid_oasis.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
    Book saveBookWithPdf(String title, String author, MultipartFile pdfFile) throws IOException;
    byte[] getPdfByBookId(Long bookId);
    String deleteBook(Long id);

}
