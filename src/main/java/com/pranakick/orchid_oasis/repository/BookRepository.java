package com.pranakick.orchid_oasis.repository;

import com.pranakick.orchid_oasis.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
