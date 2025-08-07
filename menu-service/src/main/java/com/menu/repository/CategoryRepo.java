package com.menu.repository;

import com.menu.module.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.name=:name")
    public Optional<Category> findByName(@Param("name") String name);
}
