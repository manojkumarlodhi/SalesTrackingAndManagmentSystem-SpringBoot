package com.company.salestracker.dto.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	 private String accessToken;
	    private String refreshToken;
	    private String email;
	    private Set<String> roles;
}
