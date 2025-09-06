package com.vayana.projectmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be ≤100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 40, message = "Password must be 8-40 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain: 1 uppercase, 1 lowercase, 1 digit, 1 special character"
    )
    private String password;

    // Optional fields
    @Size(max = 50, message = "Name must be ≤50 characters")
    private String name;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;
}
