package com.company.salestracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.salestracker.dto.request.UserRequest;
import com.company.salestracker.dto.response.UserResponse;
import com.company.salestracker.responseUtil.ResponseUtil;
import com.company.salestracker.service.UserService;
import com.company.salestracker.successResponse.SuccessResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	
	@PreAuthorize("hasAnyAuthority('CREATE_ADMIN', 'CREATE_SUPER_ADMIN', 'CREATE_MANAGER', 'CREATE_SALES')")
    @PostMapping
    public ResponseEntity<SuccessResponse> createUser(@RequestBody UserRequest requestDto, HttpServletRequest request) {
        UserResponse response = userService.createUser(requestDto);
        return ResponseUtil.success(
                "User created successfully",
                response,
                HttpStatus.OK,
                request.getRequestURI()
        );
    }

}
