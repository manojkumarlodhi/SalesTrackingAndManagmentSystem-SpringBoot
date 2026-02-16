package com.company.salestracker.dto.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	
	private Long userId;
    private String name;
    private String email;
    private String phone;
    private Set<String> roleNames;

}
