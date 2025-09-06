package com.vayana.projectmanagement.controller;


import com.vayana.projectmanagement.dto.LoginRequest;
import com.vayana.projectmanagement.dto.SignupRequest;
import com.vayana.projectmanagement.dto.SignupResponse;
import com.vayana.projectmanagement.entity.User;
import com.vayana.projectmanagement.service.UserService;
import com.vayana.projectmanagement.utils.JwtResponse;
import com.vayana.projectmanagement.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<SignupResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest){

        SignupResponse savedUser = userService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtUtils.generateToken(authentication);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new JwtResponse(token));

        } catch (BadCredentialsException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Bad credentials"));
        }
    }


}
