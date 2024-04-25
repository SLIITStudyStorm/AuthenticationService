package com.studyStorm.controller;

import com.studyStorm.dto.AuthRequest;
import com.studyStorm.entity.User;
import com.studyStorm.service.JwtService;
import com.studyStorm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/home")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/auth/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAllUsers() {
        return "authenticated and authorized";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody User userInfo){
        return service.addUser(userInfo);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }

}
