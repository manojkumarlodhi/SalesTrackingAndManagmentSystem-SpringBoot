package com.company.salestracker.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.company.salestracker.entity.User;
import com.company.salestracker.exception.ReasourceNotFoundException;
import com.company.salestracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class Helper {
	 private final UserRepository userRepository;

	    public User getLoggedInUser() {

	        Authentication authentication = SecurityContextHolder
	                .getContext()
	                .getAuthentication();

	        if (authentication == null || !authentication.isAuthenticated()) {
	            throw new RuntimeException("Unauthenticated access");
	        }

	        String email = authentication.getName();

	        return userRepository.findByEmail(email)
	                .orElseThrow(() -> new ReasourceNotFoundException("User not found"));
	    }
}
