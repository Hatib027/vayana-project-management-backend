package com.vayana.projectmanagement.dto;

import com.vayana.projectmanagement.entity.Role;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SignupResponse {

    private String email;
    private String name;
    private String phone;
    private Set<Role> roleSet ;
}
