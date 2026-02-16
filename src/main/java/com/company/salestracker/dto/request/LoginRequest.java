package com.company.salestracker.dto.request;

import com.company.salestracker.util.Constants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
//	 private String email;
//	    private String password;
	    
	    @NotBlank(message = Constants.EMAIL_REQUIRED)
	    @Email(message = Constants.EMAIL_VALID)
	    private String email;

	    @NotBlank(message = Constants.PASSWORD_REQUIRED)
	    @Pattern(regexp = Constants.PASSWORD_REGEX, message = Constants.PASSWORD_VALID)
	    private String password;
}
