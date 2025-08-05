package com.menu.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;
    private List<ItemsResponseDto> items;
    private LocalDateTime created_at;
    private LocalDateTime last_updated;

}
