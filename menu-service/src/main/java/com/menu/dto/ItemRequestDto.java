package com.menu.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    @NotEmpty(message = "item name is required")
    private String name;
    @NotNull(message = "item price required")
    @Min(value = 1,message = "item price cannot be zero")
    private Double price;
    @NotEmpty(message = "description is required")
    private String description;
    @NotNull(message = "item true/false required, true for available, false for not")
    private Boolean available;
}
