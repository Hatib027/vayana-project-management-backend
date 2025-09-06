package com.vayana.projectmanagement.service;

import com.vayana.projectmanagement.dto.TaskFilterRequest;
import com.vayana.projectmanagement.dto.TaskRequest;
import com.vayana.projectmanagement.dto.TaskResponse;
import com.vayana.projectmanagement.dto.TaskUpdateRequest;
import com.vayana.projectmanagement.entity.ProjectMaster;
import com.vayana.projectmanagement.entity.TaskMaster;
import com.vayana.projectmanagement.entity.User;
import com.vayana.projectmanagement.exceptions.ProjectNotFoundException;
import com.vayana.projectmanagement.exceptions.TaskNotFoundException;
import com.vayana.projectmanagement.exceptions.UnauthorizedException;
import com.vayana.projectmanagement.repository.ProjectMasterRepository;
import com.vayana.projectmanagement.repository.TaskMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskMasterServiceImpl implements TaskMasterService{

    @Autowired
    TaskMasterRepository taskMasterRepository;

    @Autowired
    ProjectMasterRepository projectMasterRepository;

    @Autowired
    UserService userService;

    @Override
    public TaskResponse addTask(TaskRequest taskRequest) {

        User user = userService.getCurrentUser();

        if(user == null){
            throw new UnauthorizedException("User not logged in !!");
        }
        Optional<ProjectMaster> projectMasterOptional = projectMasterRepository.findByUserAndId(user,taskRequest.getProjectId());

        ProjectMaster projectMaster  = projectMasterOptional.orElseThrow(()-> new ProjectNotFoundException("Project not found !!"));

        TaskMaster taskMaster = TaskMaster.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus())
                .priority(taskRequest.getPriority())
                .dueDate(taskRequest.getDueDate())
                .projectMaster(projectMaster)
                .build();

        TaskMaster taskMasterSaved = taskMasterRepository.save(taskMaster);

        return TaskResponse.fromEntity(taskMasterSaved);
    }

    @Override
    public TaskResponse viewTaskById(Long id) {
        User user = userService.getCurrentUser();

        Optional<TaskMaster> taskMasterOptional = taskMasterRepository.findByIdAndUser(id,user.getId());

        TaskMaster taskMaster = taskMasterOptional.orElseThrow(()-> new TaskNotFoundException("Task not found !!"));

        return TaskResponse.fromEntity(taskMaster);
    }

    @Override
    public TaskResponse updateTaskById(Long id, TaskUpdateRequest taskUpdateRequest) {
        User user = userService.getCurrentUser();

        Optional<TaskMaster> taskMasterOptional = taskMasterRepository.findByIdAndUser(id,user.getId());
        TaskMaster taskMaster = taskMasterOptional.orElseThrow(()-> new TaskNotFoundException("Task not found !!"));

        if (taskUpdateRequest.getTitle() != null && !taskUpdateRequest.getTitle().isBlank()) {
            taskMaster.setTitle(taskUpdateRequest.getTitle().trim());
        }

        if (taskUpdateRequest.getDescription() != null && !taskUpdateRequest.getDescription().isBlank()) {
            taskMaster.setDescription(taskUpdateRequest.getDescription().trim());
        }
        if (taskUpdateRequest.getStatus() != null) {
            taskMaster.setStatus(taskUpdateRequest.getStatus());
        }

        if (taskUpdateRequest.getPriority() != null) {
            taskMaster.setPriority(taskUpdateRequest.getPriority());
        }
        if (taskUpdateRequest.getDueDate() != null) {
            taskMaster.setDueDate(taskUpdateRequest.getDueDate());
        }

        TaskMaster taskMasterUpdated = taskMasterRepository.save(taskMaster);

        return TaskResponse.fromEntity(taskMasterUpdated);
    }

    @Override
    public String deleteTask(Long id) {
        User user = userService.getCurrentUser();

            int deleted = taskMasterRepository.deleteTask(id, user.getId());

            if (deleted == 0) {
                throw new TaskNotFoundException("Task not found !!");
            }

        return "Task deleted successfully!!";
    }

    @Override
    public Page<TaskResponse> searchTasks(TaskFilterRequest taskFilterRequest) {

        User user = userService.getCurrentUser();
        Sort sort = "desc".equalsIgnoreCase(taskFilterRequest.getOrderBy()) ? Sort.by(taskFilterRequest.getSortBy()).descending(): Sort.by(taskFilterRequest.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(taskFilterRequest.getPage(),taskFilterRequest.getSize(),sort);

        Page<TaskMaster> taskMasterPage = taskMasterRepository.searchTaskFilter(user.getId(),taskFilterRequest.getTitle(),taskFilterRequest.getStatus(),taskFilterRequest.getPriority(),taskFilterRequest.getDueDate(),taskFilterRequest.getProjectIds(),pageable);

        return taskMasterPage.map(TaskResponse::fromEntity);
    }


}
