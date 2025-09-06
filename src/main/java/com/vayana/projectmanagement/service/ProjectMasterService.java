package com.vayana.projectmanagement.service;

import com.vayana.projectmanagement.dto.ProjectRequest;
import com.vayana.projectmanagement.dto.ProjectResponse;
import com.vayana.projectmanagement.dto.ProjectUpdateRequest;
import com.vayana.projectmanagement.entity.ProjectMaster;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectMasterService {

    ProjectResponse addProject(ProjectRequest projectRequest);

    ProjectResponse getProjectById(Long id);

    ProjectResponse updateProjectById(Long id, ProjectUpdateRequest projectRequest);

    Page<ProjectResponse> searchProjects(String name, int page, int size, String sortedBy, String orderBy);

    String deleteProjectById(Long id);
}
