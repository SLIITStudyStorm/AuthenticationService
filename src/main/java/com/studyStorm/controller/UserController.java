package com.studyStorm.controller;

import com.studyStorm.entity.UserInfo;
import com.studyStorm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private ProductService service;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/auth/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAllUsers() {
        return "authenticated and authorized";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }

}
