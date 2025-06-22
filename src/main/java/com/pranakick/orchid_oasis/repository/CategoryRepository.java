package com.pranakick.orchid_oasis.repository;

import com.pranakick.orchid_oasis.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
}
