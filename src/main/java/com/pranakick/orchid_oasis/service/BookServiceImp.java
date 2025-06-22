package com.pranakick.orchid_oasis.service;

import com.pranakick.orchid_oasis.dto.BookWithoutPdfDTO;
import com.pranakick.orchid_oasis.entity.Book;
import com.pranakick.orchid_oasis.entity.Category;
import com.pranakick.orchid_oasis.exception.BookNotFoundExeption;
import com.pranakick.orchid_oasis.repository.BookRepository;
import com.pranakick.orchid_oasis.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    /**
     * This method is used to save a book with a PDF file.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param pdfFile The PDF file of the book.
     * @return The saved book.
     * @throws IOException
     */
    @Override
    public Book saveBookWithPdf(String title, String author,  List <String> categoryName, MultipartFile pdfFile) throws IOException {
        Set<Category> categorySet = categoryName.stream()
                .map(String::trim) // Remove spaces
                .filter(name -> !name.isEmpty())
                .map(name -> categoryRepository.findByName(name)
                        .orElseGet(() -> {
                            Category newCategory = new Category();
                            newCategory.setName(name);
                            return categoryRepository.save(newCategory);
                        }))
                .collect(Collectors.toSet());
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPdfFile(pdfFile.getBytes()); //Convert MultipartFile to byte[]

        // SAFE assignment, no mutation of Hibernate proxy
        book.setCategories(new HashSet<>(categorySet));


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

    @Override
    public List<BookWithoutPdfDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookWithoutPdfDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toSet())
        ))
                .collect(Collectors.toList());
    }
}
