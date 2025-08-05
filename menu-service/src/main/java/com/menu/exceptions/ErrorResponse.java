package com.menu.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private int status;              // HTTP status code (e.g. 400, 404, 500)
    private String error;            // Short error type (e.g. "Bad Request", "Internal Server Error")
    private String message;          // Detailed message or explanation
    private String path;             // API endpoint (e.g. "/api/users/123")
    private LocalDateTime timestamp; // When the error occurred

}