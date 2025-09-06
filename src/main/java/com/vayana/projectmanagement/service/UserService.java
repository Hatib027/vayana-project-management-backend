package com.vayana.projectmanagement.service;

import com.vayana.projectmanagement.dto.SignupRequest;
import com.vayana.projectmanagement.dto.SignupResponse;
import com.vayana.projectmanagement.entity.User;

public interface UserService {

    SignupResponse registerUser(SignupRequest signupRequest);

    User getCurrentUser();
}
