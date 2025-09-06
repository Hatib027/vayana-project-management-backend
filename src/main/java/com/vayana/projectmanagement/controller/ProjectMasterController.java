package com.vayana.projectmanagement.controller;


import com.vayana.projectmanagement.dto.ProjectRequest;
import com.vayana.projectmanagement.dto.ProjectResponse;
import com.vayana.projectmanagement.dto.ProjectUpdateRequest;
import com.vayana.projectmanagement.entity.ProjectMaster;
import com.vayana.projectmanagement.service.ProjectMasterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/project")
@RestController
public class ProjectMasterController {

    @Autowired
    ProjectMasterService projectMasterService;

    @PostMapping("/add-project")
    public ResponseEntity<ProjectResponse> addProject(@Valid @RequestBody ProjectRequest projectRequest){
        ProjectResponse projectResponseSaved = projectMasterService.addProject(projectRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponseSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable("id") Long id){

        ProjectResponse projectResponse = projectMasterService.getProjectById(id);

        return ResponseEntity.ok(projectResponse);
    }

    @PutMapping("/update-project/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable("id") Long id,@Valid @RequestBody ProjectUpdateRequest projectRequest){
        ProjectResponse projectResponse = projectMasterService.updateProjectById(id,projectRequest);

        return ResponseEntity.ok(projectResponse);
    }

    @GetMapping("/search-projects")
    public ResponseEntity<Page<ProjectResponse>> searchProjects(@RequestParam(required = false,value = "name") String name, @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "createdAt") String sortBy,
                                                              @RequestParam(defaultValue = "desc") String order){
        Page<ProjectResponse> projects = projectMasterService.searchProjects(name, page, limit, sortBy, order);

        return ResponseEntity.ok(projects);
    }


    @DeleteMapping("/delete-project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id){

        String message = projectMasterService.deleteProjectById(id);

        return ResponseEntity.ok(message);
    }
}
