package com.vayana.projectmanagement.repository;

import com.vayana.projectmanagement.entity.Role;
import com.vayana.projectmanagement.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(RoleType name);

    boolean existsByName(RoleType name);
}
