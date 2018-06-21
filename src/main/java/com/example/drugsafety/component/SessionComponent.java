package com.example.drugsafety.component;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.drugsafety.entity.acl.User;
import com.example.drugsafety.service.UserService;

@Component
@Scope("session")
public class SessionComponent {

    private User user;
    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        if (this.user == null) {
            this.user = userService.findByUsername(
                    ((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName()
            );
        }
        return this.user;
    }
}
