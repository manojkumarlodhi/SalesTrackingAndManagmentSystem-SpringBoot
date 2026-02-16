package com.company.salestracker.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.salestracker.dto.request.LoginRequest;
import com.company.salestracker.responseUtil.ResponseUtil;
import com.company.salestracker.service.AuthService;
import com.company.salestracker.successResponse.SuccessResponse;

import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	 @Autowired
	    private AuthService authService;

//	    @PostMapping("/login")
//	    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//	        return ResponseEntity.ok(authService.login(request));
//	    }
//
	  
	 
	  @PostMapping("/login")
	    public ResponseEntity<SuccessResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
	        Map<String, String> tokens = authService.login(request);

	        return ResponseUtil.success(
	                "Login successful",
	                tokens,
	                HttpStatus.OK,
	                httpRequest.getRequestURI()
	        );
	    }
	  
	  @PostMapping("/refresh")
	    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
	        return ResponseEntity.ok(
	                authService.refreshToken(request.get("refreshToken"))
	        );
	    }

	   
}
