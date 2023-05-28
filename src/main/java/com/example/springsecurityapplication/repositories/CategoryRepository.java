package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    com.example.springsecurityapplication.models.Category findByTitle(String title);

    @Query("SELECT c.title FROM Category c WHERE c.id = :id")
    String findNameById(Integer id);

}
