package com.pranakick.orchid_oasis.service;

import com.pranakick.orchid_oasis.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
    Book saveBookWithPdf(String title, String author, MultipartFile pdfFile) throws IOException;
    public byte[] getPdfByBookId(Long bookId);
}
