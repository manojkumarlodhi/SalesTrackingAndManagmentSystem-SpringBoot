package com.company.salestracker.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	private LocalDateTime timestamp;
    private int status;
    private String error;
    private Object message;
    private String path;
    private Object errors;

}
