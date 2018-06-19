package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.drugsafety.entity.acl.User;

public interface UserRepository extends JpaRepository<User, String>{
	public User findByEmail(String email);

	public User findByEmpId(String empId);
}
