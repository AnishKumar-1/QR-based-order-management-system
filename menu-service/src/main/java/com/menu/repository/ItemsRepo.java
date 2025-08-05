package com.menu.repository;

import com.menu.module.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemsRepo extends JpaRepository<Items,Long> {
    public Optional<Items> findByName(String name);
}
