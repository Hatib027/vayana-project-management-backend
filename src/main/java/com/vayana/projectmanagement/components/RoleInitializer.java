package com.vayana.projectmanagement.components;

import com.vayana.projectmanagement.entity.Role;
import com.vayana.projectmanagement.enums.RoleType;
import com.vayana.projectmanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByName(RoleType.ROLE_USER)) {
            Role userRole = new Role();
            userRole.setName(RoleType.ROLE_USER);
            roleRepository.save(userRole);
            System.out.println("ROLE_USER created!");
        }

        if (!roleRepository.existsByName(RoleType.ROLE_ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(RoleType.ROLE_ADMIN);
            roleRepository.save(adminRole);
            System.out.println("ROLE_ADMIN created!");
        }
    }
}