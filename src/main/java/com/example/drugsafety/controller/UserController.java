package com.example.drugsafety.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.drugsafety.component.SessionComponent;
import com.example.drugsafety.entity.acl.User;
import com.example.drugsafety.model.ResponseModel;
import com.example.drugsafety.repository.UserRepository;
import com.example.drugsafety.service.AuthService;
import com.example.drugsafety.service.UserProviderService;
import com.example.drugsafety.service.UserService;
import com.example.drugsafety.service.util.ResponseService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Scope("session")
@RequestMapping(value = "/login")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private SessionComponent sessionComponent;

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseService responseService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	EntityManager entityManager;

	@RequestMapping(value = "/login", method = RequestMethod.GET)

	public ResponseModel login() {

		return responseService.response().message("Login page");
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)

	public ResponseModel test() {

		return responseService.response().message("Test");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseModel login(@RequestParam("email") String email, @RequestParam("password") String password) {
		User user = this.userRepository.findByEmail(email);
		if (user != null && user.isActive()) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
			token.setDetails(user);
			try {
				Authentication auth = authenticationManager.authenticate(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
				return responseService.response().data(user);
			} catch (Exception e) {
				this.logger.error(e.getMessage(), e);
			}
		}
		return responseService.response().message("Invalid username/password ").data(user)
				.statusCode(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseModel createNewUser(@Valid User user) {
		User userExists = userService.findByUsername(user.getEmail(), user.getEmpId());
		if (userExists != null) {
			return responseService.response().data(user)
					.message("Already a user exists in the system with given email or employee id");
		}

		userService.save(user);
		return responseService.response().data(user).message("Registered Successfully!");
	}

	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
	public ResponseModel userInfo() {
		return responseService.response(this.sessionComponent.getCurrentUser());
	}

	@RequestMapping(value = "/user/update-profile", method = RequestMethod.PUT)
	@Transactional
	public ResponseModel updateProfile(@PathVariable("id") String id, @Valid User data) {
		User user = this.entityManager.find(User.class, id);
		if (user == null) {
			return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
		}
		this.entityManager.merge(data);
		this.entityManager.persist(user);
		this.entityManager.flush();
		return this.responseService.response(user);
	}
}
