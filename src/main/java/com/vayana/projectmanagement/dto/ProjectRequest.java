package com.vayana.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
    @NotBlank(message = "Project name must be required !!")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Project description must be required !!")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
