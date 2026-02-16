package com.company.salestracker.responseUtil;



import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.salestracker.successResponse.SuccessResponse;


public class ResponseUtil {
	public static ResponseEntity<SuccessResponse> success(
            String message,
            Object data,
            HttpStatus status,
            String path) {

        SuccessResponse response = new SuccessResponse(
            LocalDateTime.now(),
            status.value(),
            message,
            data,
            path
        );

        return new ResponseEntity<>(response, status);
    }
    public static ResponseEntity<SuccessResponse> success(
            String message,
            Object data,
            HttpStatus status) {

        return success(message, data, status, null);
    }
    public static ResponseEntity<SuccessResponse> success(
            String message,
            HttpStatus status) {

        return success(message, null, status, null);
    }

}
