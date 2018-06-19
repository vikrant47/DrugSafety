package com.example.drugsafety.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.drugsafety.entity.acl.User;
import com.example.drugsafety.entity.acl.Role;
import com.example.drugsafety.model.ResponseModel;
import com.example.drugsafety.repository.RoleRepository;
import com.example.drugsafety.repository.UserRepository;
import com.example.drugsafety.service.CrudService;
import com.example.drugsafety.service.UserService;
import com.example.drugsafety.service.util.Messages;
import com.example.drugsafety.service.util.ResponseService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResponseService responseService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Messages messages;

    @Autowired
    private CrudService crudService;

    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
    @Transactional
    public ResponseModel addNewUser(@Valid User data) {
        User user = this.userService.findByUsername(data.getEmail(), data.getEmail());
        if (user != null) {
            return this.responseService.response(user).statusCode(HttpStatus.BAD_REQUEST).action()
                    .error(messages.get("message.error"), messages.get("user.error.duplicate")).response();
        }
        this.entityManager.persist(data);
        this.entityManager.flush();
        return this.responseService.response(data);
    }

    @RequestMapping(value = "/user/{id}/set-roles", method = RequestMethod.POST)
    @Transactional
    public ResponseModel addNewUser(@PathVariable String id, @RequestParam String[] roleIds) {
        User user = this.userService.findById(id);
        if (user == null) {
            return this.responseService.response().notFoundIfNull().action()
                    .error(messages.get("message.error"), messages.get("user.notfound.by.id")).response();
        }
        this.userService.setRoles(user, roleIds);
        return this.responseService.response(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseModel updateUser(@PathVariable("id") String id, @Valid User data) {
        User user = this.crudService.update(User.class, id, data);
        if (user == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        return this.responseService.response(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseModel deleteUserById(@PathVariable("id") String id) {
        User user = this.crudService.remove(User.class, id);
        if (user == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        return this.responseService.response(user);

    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseModel getAllUser() {
        return this.responseService.response(this.userRepository.findAll());
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseModel getUserById(@PathVariable("id") String id) {
        return this.responseService.response(this.userRepository.findById(id)).notFoundIfNull();

    }

    @RequestMapping(value = "/role/{roleName}/users", method = RequestMethod.GET)
    public ResponseModel getUserByRole(@PathVariable("roleName") String roleName) {
        Role role = this.roleRepository.findByName(roleName);
        return this.responseService.response(role.getUsers()).notFoundIfNull();
    }
}
