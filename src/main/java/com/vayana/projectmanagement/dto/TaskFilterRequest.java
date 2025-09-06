package com.vayana.projectmanagement.dto;

import com.vayana.projectmanagement.enums.TaskPriority;
import com.vayana.projectmanagement.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskFilterRequest {
    private List<Long> projectIds;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;

    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String orderBy = "desc";
}
