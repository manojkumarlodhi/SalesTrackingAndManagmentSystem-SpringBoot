package com.company.salestracker.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.company.salestracker.dto.request.LoginRequest;
import com.company.salestracker.exception.UnauthorizedException;
import com.company.salestracker.security.JwtTokenProvider;
import com.company.salestracker.service.AuthService;
@Service
public class AuthServiceImpl implements AuthService {
	
	   @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtTokenProvider jwtTokenProvider;

	    @Autowired
	    private UserDetailsService userDetailsService;

	    @Override
	    public Map<String, String> login(LoginRequest request) {

	        try {

	            Authentication authentication =
	                    authenticationManager.authenticate(
	                            new UsernamePasswordAuthenticationToken(
	                                    request.getEmail(),
	                                    request.getPassword()
	                            )
	                    );

	            UserDetails userDetails =
	                    (UserDetails) authentication.getPrincipal();

	            String accessToken =
	                    jwtTokenProvider.generateAccessToken(userDetails);

	            String refreshToken =
	                    jwtTokenProvider.generateRefreshToken(userDetails.getUsername());

	            return Map.of(
	                    "accessToken", accessToken,
	                    "refreshToken", refreshToken
	            );

	        } catch (Exception ex) {
	            throw new UnauthorizedException("Invalid username or password");
	        }
	    }


	    @Override
	    public Map<String, String> refreshToken(String refreshToken) {

	        if (!jwtTokenProvider.validateToken(refreshToken)) {
	            throw new RuntimeException("Invalid Refresh Token");
	        }

	        String username =
	                jwtTokenProvider.extractUsername(refreshToken);

	        UserDetails userDetails =
	                userDetailsService.loadUserByUsername(username);

	        String newAccessToken =
	                jwtTokenProvider.generateAccessToken(userDetails);

	        return Map.of("accessToken", newAccessToken);
	    }

}
