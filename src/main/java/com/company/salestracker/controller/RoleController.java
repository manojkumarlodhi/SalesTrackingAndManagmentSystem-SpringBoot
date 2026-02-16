package com.company.salestracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.salestracker.dto.request.CreateRoleRequest;
import com.company.salestracker.responseUtil.ResponseUtil;
import com.company.salestracker.service.RoleService;
import com.company.salestracker.successResponse.SuccessResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
	
	
	private final RoleService roleService;
	
	@PreAuthorize("hasAnyAuthority('CREATE_ADMIN', 'CREATE_SUPER_ADMIN', 'CREATE_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createRole(@RequestBody CreateRoleRequest request, HttpServletRequest httpRequest) {
        roleService.createRole(request);

        return ResponseUtil.success(
                "Role created successfully",
                null,
                HttpStatus.OK,
                httpRequest.getRequestURI()
        );
    }

}
