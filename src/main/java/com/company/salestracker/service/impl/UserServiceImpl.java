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
import com.company.salestracker.exception.UnauthorizedException;
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

        if (loggedIn == null) {
            throw new UnauthorizedException("User not authenticated");
        }

        boolean isSuperAdmin = loggedIn.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));

        boolean isAdmin = loggedIn.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equalsIgnoreCase("ADMIN"));

        boolean isSalesManager = loggedIn.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equalsIgnoreCase("SALES_MANAGER"));

        boolean isSalesExecutive = loggedIn.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equalsIgnoreCase("SALES_EXECUTIVE"));

        boolean isViewer = loggedIn.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equalsIgnoreCase("VIEWER"));
        if (isViewer || isSalesExecutive) {
            throw new UnauthorizedException("You are not allowed to create users");
        }

        Set<String> requestedRoles = request.getRoleNames();

        if (requestedRoles == null || requestedRoles.isEmpty()) {
            throw new IllegalArgumentException("At least one role must be provided");
        }

       
        for (String roleName : requestedRoles) {
            String upperCaseRoleName = roleName.toUpperCase();

            boolean roleExists = roleRepository
                    .findByRoleNameAndIsDeleteFalse(upperCaseRoleName)
                    .isPresent();

            if (!roleExists) {
                throw new ReasourceNotFoundException(
                        "Role not configured in system: " + roleName +
                        ". Please contact SUPER_ADMIN.");
            }
        }
        for (String roleName : requestedRoles) {
            String upperCaseRoleName = roleName.toUpperCase();

            if (isSuperAdmin) {
                if (!(upperCaseRoleName.equals("SUPER_ADMIN") || upperCaseRoleName.equals("ADMIN"))) {
                    throw new UnauthorizedException(
                            "SUPER_ADMIN can only create SUPER_ADMIN or ADMIN");
                }
            } else if (isAdmin) {
                if (!upperCaseRoleName.equals("SALES_MANAGER")) {
                    throw new UnauthorizedException(
                            "ADMIN can only create SALES_MANAGER");
                }
            } else if (isSalesManager) {
                if (!upperCaseRoleName.equals("SALES_EXECUTIVE")) {
                    throw new UnauthorizedException(
                            "SALES_MANAGER can only create SALES_EXECUTIVE");
                }
            } else {
                throw new UnauthorizedException("You are not allowed to create users");
            }
        }

        userRepository.findByEmailAndIsDeleteFalseAndStatus(
                request.getEmail().toLowerCase(), Status.ACTIVE)
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException(
                            "Email already exists: " + request.getEmail());
                });

   
        Set<Role> roles = requestedRoles.stream()
                .map(roleName -> roleRepository
                        .findByRoleNameAndIsDeleteFalse(roleName.toUpperCase())
                        .orElseThrow(() ->
                                new ReasourceNotFoundException(
                                        "Role not found: " + roleName)))
                .collect(Collectors.toSet());

     
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .createdBy(loggedIn)
                .status(Status.ACTIVE)
                .isDelete(false)
                .build();

        userRepository.save(user);

        // Set the owner based on role hierarchy
        User owner = determineOwner(loggedIn, isSuperAdmin, isAdmin, isSalesManager);
        user.setOwner(owner);
        userRepository.save(user);

        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roleNames(user.getRoles().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toSet()))
                .build();
    }

   
    private User determineOwner(User loggedIn, boolean isSuperAdmin, boolean isAdmin, boolean isSalesManager) {
        
        if (isSuperAdmin) {
            return loggedIn; 
        }
        else if (isAdmin) {
            return loggedIn; 
        }
        else if (isSalesManager) {
            return findRootOwner(loggedIn);
        }
        else {
            return loggedIn;
        }
    }

    
    private User findRootOwner(User user) {
        if (user == null) {
            return null;
        }
        if (user.getOwner() != null && !user.getOwner().equals(user)) {
            return findRootOwner(user.getOwner());
        }
        return user;
    }
}