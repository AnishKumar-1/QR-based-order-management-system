package com.table.service.repository;

import com.table.service.model.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepo extends JpaRepository<Tables, Long> {
    //find by tableNumber
    public Optional<Tables> findByTableNumber(Integer tableNumber);
}
