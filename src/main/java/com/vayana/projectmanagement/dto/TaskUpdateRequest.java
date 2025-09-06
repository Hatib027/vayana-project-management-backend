package com.vayana.projectmanagement.dto;

import com.vayana.projectmanagement.enums.TaskPriority;
import com.vayana.projectmanagement.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskUpdateRequest {

    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;
}
