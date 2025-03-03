package com.pranakick.orchid_oasis.service;

import com.pranakick.orchid_oasis.entity.Book;
import com.pranakick.orchid_oasis.exception.BookNotFoundExeption;
import com.pranakick.orchid_oasis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    private final BookRepository bookRepository;

    /**
     * This method is used to save a book with a PDF file.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param pdfFile The PDF file of the book.
     * @return The saved book.
     * @throws IOException
     */
    @Override
    public Book saveBookWithPdf(String title, String author, MultipartFile pdfFile) throws IOException {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPdfFile(pdfFile.getBytes()); //Convert MultipartFile to byte[]
        return bookRepository.save(book);
    }

    /**
     * This method is used to get the PDF file of a book by its ID.
     * @param bookId
     * @return The PDF file of the book.
     */
    @Override
    public byte[] getPdfByBookId(Long bookId){
        return bookRepository.findById(bookId)
                .map(Book::getPdfFile)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    /**
     * This method is used to delete a book by its ID.
     * @param bookId
     * @return A message indicating the success of the operation.
     */
    @Override
    @Transactional
    public String deleteBook (Long bookId){
        Optional<Book> deleteCandidate = bookRepository.findById(bookId);
        if(deleteCandidate.isEmpty()){
            throw new BookNotFoundExeption("The book was not found");
        }
        //Book bookToDelete = deleteCandidate.get();
        bookRepository.deleteById(bookId);
        return "The Book with ID " + bookId + " was deleted successfully";
    }
}
