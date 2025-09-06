package com.vayana.projectmanagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vayana.projectmanagement.enums.TaskPriority;
import com.vayana.projectmanagement.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_master")
public class TaskMaster extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDate dueDate;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "project_id",nullable = false)
    ProjectMaster projectMaster;
}
