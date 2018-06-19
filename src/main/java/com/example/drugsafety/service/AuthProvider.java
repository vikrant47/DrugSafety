package com.example.drugsafety.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.drugsafety.entity.acl.User;
import com.example.drugsafety.repository.RoleRepository;
import com.example.drugsafety.repository.UserRepository;

@Service
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getPrincipal() + "";
		String password = authentication.getCredentials() + "";

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new BadCredentialsException("1000");
		}
		if (!user.isActive()) {
			throw new DisabledException("1001");
		}
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("1000");
		}
		
		return new UsernamePasswordAuthenticationToken(email, password,
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
