package com.company.salestracker.security;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.company.salestracker.entity.User;
import com.company.salestracker.exception.ReasourceNotFoundException;
import com.company.salestracker.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {	
	 @Autowired
	    private UserRepository userRepo;

	    @Override
	    public UserDetails loadUserByUsername(String email) {
	        User user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new ReasourceNotFoundException("User not found"));

	        Set<SimpleGrantedAuthority> authorities =
	            user.getRoles().stream()
	                .flatMap(r -> r.getPermissions().stream())
	                .map(p -> new SimpleGrantedAuthority(p.getPermissionCode()))
	                .collect(Collectors.toSet());

	        return new org.springframework.security.core.userdetails.User(
	                user.getEmail(),
	                user.getPassword(),
	                authorities
	        );
	    }
}
