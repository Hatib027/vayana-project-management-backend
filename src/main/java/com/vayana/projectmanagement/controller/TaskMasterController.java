package com.vayana.projectmanagement.controller;

import com.vayana.projectmanagement.dto.TaskFilterRequest;
import com.vayana.projectmanagement.dto.TaskRequest;
import com.vayana.projectmanagement.dto.TaskResponse;
import com.vayana.projectmanagement.dto.TaskUpdateRequest;
import com.vayana.projectmanagement.service.TaskMasterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskMasterController {

    @Autowired
    TaskMasterService taskMasterService;

    @PostMapping("/add-task")
    public ResponseEntity<TaskResponse> addProject(@Valid @RequestBody TaskRequest taskRequest){
        TaskResponse taskResponse = taskMasterService.addTask(taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> viewTaskById(@PathVariable("id") Long id){
        TaskResponse taskResponse = taskMasterService.viewTaskById(id);
        return ResponseEntity.ok(taskResponse);
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<TaskResponse> updateTaskById(@PathVariable("id") Long id,@Valid @RequestBody TaskUpdateRequest taskRequest){

        TaskResponse taskResponse = taskMasterService.updateTaskById(id,taskRequest);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable("id")Long id){
        String message = taskMasterService.deleteTask(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/search-tasks")
    public ResponseEntity<Page<TaskResponse>> searchTasks(@RequestBody TaskFilterRequest taskFilterRequest){

        Page<TaskResponse> taskResponsePage = taskMasterService.searchTasks(taskFilterRequest);

        return ResponseEntity.ok(taskResponsePage);

    }

}
