package com.vayana.projectmanagement.dto;

import com.vayana.projectmanagement.entity.TaskMaster;
import com.vayana.projectmanagement.enums.TaskPriority;
import com.vayana.projectmanagement.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    ProjectInfoDTO projectInfoDTO;

    public static TaskResponse fromEntity(TaskMaster taskMaster){
        ProjectInfoDTO projectInfoDTO = new ProjectInfoDTO(taskMaster.getProjectMaster().getId(),taskMaster.getProjectMaster().getName());
        return TaskResponse.builder().id(taskMaster.getId()).createdAt(taskMaster.getCreatedAt()).updatedAt(taskMaster.getUpdatedAt()).title(taskMaster.getTitle())
                .description(taskMaster.getDescription()).dueDate(taskMaster.getDueDate()).priority(taskMaster.getPriority()).status(taskMaster.getStatus()).projectInfoDTO(projectInfoDTO).build();
    }

}
