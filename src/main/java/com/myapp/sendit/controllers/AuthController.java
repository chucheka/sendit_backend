package com.myapp.sendit.controllers;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.sendit.dto.BecomeRiderDto;
import com.myapp.sendit.dto.LoginRequest;
import com.myapp.sendit.dto.SignUpRequest;
import com.myapp.sendit.services.AuthService;





@RestController
@RequestMapping("/api/v1/auth")
public class AuthController{
	@Autowired
	AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    	return authService.authenticateUser(loginRequest);
    	    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    return authService.registerUser(signUpRequest);
    }
    @PostMapping("/rider")
    public ResponseEntity<?> registerAsRider(@Valid @RequestBody BecomeRiderDto riderDto) {
    return authService.registerAsRider(riderDto);
    }
    @PostMapping("/customer")
    public ResponseEntity<?> registerAsCustomer11(@Valid @RequestBody SignUpRequest customerDto) {
    return authService.registerAsCustomer(customerDto);
    }
}