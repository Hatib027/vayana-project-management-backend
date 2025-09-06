package com.vayana.projectmanagement.service;


import com.vayana.projectmanagement.dto.SignupRequest;
import com.vayana.projectmanagement.dto.SignupResponse;
import com.vayana.projectmanagement.entity.Role;
import com.vayana.projectmanagement.entity.User;
import com.vayana.projectmanagement.enums.RoleType;
import com.vayana.projectmanagement.repository.RoleRepository;
import com.vayana.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public SignupResponse registerUser(SignupRequest signupRequest) {

        Optional<User> optionalUser = userRepository.findByEmail(signupRequest.getEmail());
        User user = new User();

        if (optionalUser.isPresent()) {

                throw new RuntimeException("User already registered with this email.");

        }


        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        user.setName(signupRequest.getName());
        Role role = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);


    User userSaved =    userRepository.save(user);



        return SignupResponse.builder().email(userSaved.getEmail()).phone(userSaved.getPhone()).name(userSaved.getName()).roleSet(userSaved.getRoles()).build();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                return userRepository.findByEmail(email).orElse(null);
            }
        }
        return null;
    }
}
