package com.pranakick.orchid_oasis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name ="book_id")
    private Long id;

    @Column(name = "book_title")
    private String title;

    @Column(name= "book_author")
    private String author;

    @Column(name = "cover_image_path")
    private String coverImagePath;

    @Lob
    @Column(name = "pdf_file", columnDefinition = "BLOB") // columnDefinition = "BLOB": Ensures correct mapping in H2.
    private byte[] pdfFile;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

}
