package com.menu.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.menu.module.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemsResponseDto {

    private Long id;
    private String name;
    private Double price;
    private String description;
    private Boolean available;
    private LocalDateTime created_at;
    private LocalDateTime last_updated;

}
