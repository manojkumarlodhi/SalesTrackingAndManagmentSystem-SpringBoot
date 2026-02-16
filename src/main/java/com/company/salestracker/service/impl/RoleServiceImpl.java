package com.company.salestracker.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.company.salestracker.dto.request.CreateRoleRequest;
import com.company.salestracker.entity.Permission;
import com.company.salestracker.entity.Role;
import com.company.salestracker.entity.User;
import com.company.salestracker.exception.ReasourceNotFoundException;
import com.company.salestracker.exception.RoleAlreadyExistsException;
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
	   

	  
	    Role existingRole = roleRepository
	            .findByRoleNameAndIsDeleteFalse(request.getRoleName())
	            .orElse(null);

	    if (existingRole != null) {
	        throw new RoleAlreadyExistsException("Role already exists: " + request.getRoleName());
	    }



	    Set<Permission> permissionSet = request.getPermissions()
	            .stream()
	            .map(code -> permissionRepository.findByPermissionCode(code)
	                    .orElseThrow(() ->
	                            new ReasourceNotFoundException("Permission not found: " + code)))
	            .collect(Collectors.toSet());
	    
	    User owner = loggedInUser.getRoles().stream()
	            .anyMatch(r -> r.getRoleName().equalsIgnoreCase("SUPER_ADMIN")) ? null : loggedInUser;


	    Role role = Role.builder()
	            .roleName(request.getRoleName())
	            .description(request.getDescription())
	            .permissions(permissionSet)
	            .createBy(loggedInUser)  
	            .owner(owner) 
	            .isDelete(false)
	            .build();

	    roleRepository.save(role);
	}


}
