package com.vayana.projectmanagement.service;

import com.vayana.projectmanagement.dto.TaskFilterRequest;
import com.vayana.projectmanagement.dto.TaskRequest;
import com.vayana.projectmanagement.dto.TaskResponse;
import com.vayana.projectmanagement.dto.TaskUpdateRequest;
import org.springframework.data.domain.Page;

public interface TaskMasterService{

    public TaskResponse addTask(TaskRequest taskRequest);

    public TaskResponse viewTaskById(Long id);

    public TaskResponse updateTaskById(Long id,TaskUpdateRequest taskUpdateRequest);

    public String deleteTask(Long id);

    Page<TaskResponse> searchTasks(TaskFilterRequest taskFilterRequest);

}
