package com.company.salestracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.salestracker.entity.Role;
import com.company.salestracker.entity.RoleEnum;
import com.company.salestracker.entity.User;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
	Optional<Role> findByRoleNameAndIsDeleteFalse(String roleName);
	Optional<Role> findByRoleNameAndOwnerAndIsDeleteFalse(
            String roleName,
            User owner
    );
	
	

}
