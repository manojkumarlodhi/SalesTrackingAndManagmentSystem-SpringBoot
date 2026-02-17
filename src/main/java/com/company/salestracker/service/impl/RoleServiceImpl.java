package com.company.salestracker.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.company.salestracker.dto.request.CreateRoleRequest;
import com.company.salestracker.entity.Permission;
import com.company.salestracker.entity.Role;
import com.company.salestracker.entity.RoleEnum;
import com.company.salestracker.entity.User;
import com.company.salestracker.exception.ReasourceNotFoundException;
import com.company.salestracker.exception.RoleAlreadyExistsException;
import com.company.salestracker.exception.UnauthorizedException;
import com.company.salestracker.repository.PermissionRepository;
import com.company.salestracker.repository.RoleRepository;
import com.company.salestracker.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl  implements RoleService{
	
	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final Helper helper;

	@Override
	public void createRole(CreateRoleRequest request) {

	    User loggedInUser = helper.getLoggedInUser();

	    if (loggedInUser == null) {
	        throw new UnauthorizedException("User not authenticated");
	    }

	    boolean isSuperAdmin = loggedInUser.getRoles().stream()
	            .anyMatch(r -> r.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));

	    boolean isAdmin = loggedInUser.getRoles().stream()
	            .anyMatch(r -> r.getRoleName().equalsIgnoreCase("ADMIN"));

	    boolean isSalesManager = loggedInUser.getRoles().stream()
	            .anyMatch(r -> r.getRoleName().equalsIgnoreCase("SALES_MANAGER"));

	    boolean isSalesExecutive = loggedInUser.getRoles().stream()
	            .anyMatch(r -> r.getRoleName().equalsIgnoreCase("SALES_EXECUTIVE"));

	    boolean isViewer = loggedInUser.getRoles().stream()
	            .anyMatch(r -> r.getRoleName().equalsIgnoreCase("VIEWER"));

	   
	    if (isSalesManager || isSalesExecutive || isViewer) {
	        throw new UnauthorizedException("You are not allowed to create roles");
	    }

	    if (!isSuperAdmin && !isAdmin) {
	        throw new UnauthorizedException("Only SUPER_ADMIN or ADMIN can create roles");
	    }

	    String roleName = request.getRoleName().trim().toUpperCase();
	    try {
	        RoleEnum.valueOf(roleName);
	    } catch (IllegalArgumentException e) {
	        throw new ReasourceNotFoundException("Invalid role name: " + roleName);
	    }

	   
	    if ((roleName.equals("SUPER_ADMIN") || roleName.equals("ADMIN")) && !isSuperAdmin) {
	        throw new UnauthorizedException(
	                "Only SUPER_ADMIN can create SUPER_ADMIN or ADMIN role");
	    }

	    roleRepository.findByRoleNameAndIsDeleteFalse(roleName)
	            .ifPresent(r -> {
	                throw new RoleAlreadyExistsException("Role already exists: " + roleName);
	            });

	    
	    Set<Permission> permissionSet = request.getPermissions()
	            .stream()
	            .map(code -> permissionRepository
	                    .findByPermissionCode(code)
	                    .orElseThrow(() ->
	                            new ReasourceNotFoundException("Permission not found: " + code)))
	            .collect(Collectors.toSet());

	    User owner = isSuperAdmin ? null : loggedInUser;

	    Role role = Role.builder()
	            .roleName(roleName)
	            .description(request.getDescription())
	            .permissions(permissionSet)
	            .createBy(loggedInUser)
	            .owner(owner)
	            .isDelete(false)
	            .build();

	    roleRepository.save(role);
	}

}
