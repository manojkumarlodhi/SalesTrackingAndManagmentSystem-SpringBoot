package com.company.salestracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.salestracker.entity.Status;
import com.company.salestracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmail(String email);
	public  Boolean existsByEmail(String email);
	 Optional<User> findByEmailAndIsDeleteFalse(String email);
	 Optional<User> findByEmailAndIsDeleteFalseAndStatus(String email, Status status);
	 List<User> findByOwnerAndIsDeleteFalse(User owner);


}
