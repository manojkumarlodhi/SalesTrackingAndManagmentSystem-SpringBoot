package com.company.salestracker.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.salestracker.dto.request.UserRequest;
import com.company.salestracker.dto.response.UserResponse;
import com.company.salestracker.entity.Role;
import com.company.salestracker.entity.Status;
import com.company.salestracker.entity.User;
import com.company.salestracker.exception.EmailAlreadyExistsException;
import com.company.salestracker.exception.ReasourceNotFoundException;
import com.company.salestracker.repository.RoleRepository;
import com.company.salestracker.repository.UserRepository;
import com.company.salestracker.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final Helper securityHelper;

	@Override
	public UserResponse createUser(UserRequest request) {

		User loggedIn = securityHelper.getLoggedInUser();

		User owner = loggedIn.getOwner() != null ? loggedIn.getOwner() : loggedIn;

		userRepository.findByEmailAndIsDeleteFalseAndStatus(request.getEmail(), Status.ACTIVE).ifPresent(u -> {
			throw new EmailAlreadyExistsException("Email already exists");
		});

		Set<Role> roles = request.getRoleNames().stream()
				.map(roleName -> roleRepository.findByRoleNameAndOwnerAndIsDeleteFalse(roleName, owner)
						.orElseThrow(() -> new ReasourceNotFoundException("Role not found: " + roleName)))
				.collect(Collectors.toSet());

		User user = User.builder().name(request.getName()).email(request.getEmail()).phone(request.getPhone())
				.password(passwordEncoder.encode(request.getPassword())).roles(roles).owner(owner).createdBy(loggedIn)
				.status(Status.ACTIVE).isDelete(false).build();

		userRepository.save(user);

		return UserResponse.builder().userId(user.getUserId()).name(user.getName()).email(user.getEmail())
				.phone(user.getPhone())
				.roleNames(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet())).build();
	}

}
