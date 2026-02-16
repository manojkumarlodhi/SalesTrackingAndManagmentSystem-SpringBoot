package com.company.salestracker.dto.request;

import java.util.Set;

import com.company.salestracker.util.Constants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
//	 	private String name;
//	    private String email;
//	    private String password;
//	    private String phone;
//	    private Set<String> roleNames;
	    
	    
	    
	    @NotBlank(message = Constants.NAME_REQUIRED)
	    @Size(max = 50, message = Constants.NAME_MAX)
	    private String name;

	    @NotBlank(message = Constants.EMAIL_REQUIRED)
	    @Email(message = Constants.EMAIL_VALID)
	    private String email;

	    @NotBlank(message = Constants.PHONE_REQUIRED)
	    @Pattern(regexp = Constants.PHONE_REGEX, message = Constants.PHONE_VALID)
	    private String phone;

	    @NotBlank(message = Constants.PASSWORD_REQUIRED)
	    @Pattern(regexp = Constants.PASSWORD_REGEX, message = Constants.PASSWORD_VALID)
	    private String password;

	    @NotEmpty(message = Constants.ROLE_REQUIRED)
	    private Set<String> roleNames;
}
