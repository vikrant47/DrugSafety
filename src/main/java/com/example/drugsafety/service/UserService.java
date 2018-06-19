package com.example.drugsafety.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.drugsafety.entity.acl.User;
import com.example.drugsafety.entity.acl.Role;
import com.example.drugsafety.repository.RoleRepository;
import com.example.drugsafety.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private EntityManager entityManager;

	public User findByUsername(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			user = userRepository.findByEmpId(username);
		}
		return user;
	}

	public User findByUsername(String email, String empId) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			user = userRepository.findByEmpId(empId);
		}
		return user;
	}

	public User findById(String id) {
		Optional<User> option = this.userRepository.findById(id);
		return option.get();
	}

	public User setRoles(User user, String[] roleIds) {
		if (user != null) {
			Query rolesQuery = this.entityManager.createQuery("select role from Role where id in :roleIds")
					.setParameter("roleIds", roleIds);
			Set<Role> roles = new HashSet<>();
			roles.addAll(rolesQuery.getResultList());
			user.setRoles(roles);
			this.entityManager.persist(user);
			this.entityManager.flush();
		}
		return user;
	}

	public void save(User user) {
		user.setPlainPassword(user.getPassword());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user);
	}

	public User getCurrentUser() {
		return new User();
	}

}
