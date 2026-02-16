package com.company.salestracker.service;

import java.util.Map;

import com.company.salestracker.dto.request.LoginRequest;

public interface AuthService {
	Map<String, String> login(LoginRequest request);

    Map<String, String> refreshToken(String refreshToken);
}
