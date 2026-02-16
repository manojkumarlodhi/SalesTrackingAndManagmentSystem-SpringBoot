package com.company.salestracker.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleRequest {
//	    private String roleName;
//	    private String description;
//	    private Set<String> permissions;
	    
	    
	    @NotBlank(message = "Role name is required")
	    private String roleName;

	    @NotBlank(message = "Description is required")
	    private String description;

	    @NotEmpty(message = "Permissions cannot be empty")
	    private Set<String> permissions;
}
