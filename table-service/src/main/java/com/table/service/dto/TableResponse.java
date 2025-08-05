package com.table.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableResponse {
    private Long id;
    private Integer tableNumber;
    private Boolean isActive;

}
