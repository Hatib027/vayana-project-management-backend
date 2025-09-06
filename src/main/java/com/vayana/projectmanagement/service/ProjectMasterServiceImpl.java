package com.vayana.projectmanagement.service;

import com.vayana.projectmanagement.dto.ProjectRequest;
import com.vayana.projectmanagement.dto.ProjectResponse;
import com.vayana.projectmanagement.dto.ProjectUpdateRequest;
import com.vayana.projectmanagement.entity.ProjectMaster;
import com.vayana.projectmanagement.entity.User;
import com.vayana.projectmanagement.exceptions.DuplicateProjectException;
import com.vayana.projectmanagement.exceptions.ProjectNotFoundException;
import com.vayana.projectmanagement.exceptions.UnauthorizedException;
import com.vayana.projectmanagement.repository.ProjectMasterRepository;
import com.vayana.projectmanagement.repository.TaskMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService{

    @Autowired
    ProjectMasterRepository projectMasterRepository;

    @Autowired
    TaskMasterRepository taskMasterRepository;

    @Autowired
    UserService userService;

    @Override
    public ProjectResponse addProject(ProjectRequest projectRequest) {

        User user = userService.getCurrentUser();

        if(user == null){
            throw new UnauthorizedException("User not logged in !!");
        }
        if (projectMasterRepository.existsByUserAndName(user, projectRequest.getName())) {
            throw new RuntimeException("You already have a project with this name!");
        }

        ProjectMaster projectMaster = new ProjectMaster();
        projectMaster.setName(projectRequest.getName());
        projectMaster.setDescription(projectRequest.getDescription());
        projectMaster.setUser(user);

        ProjectMaster projectMasterSaved = this.projectMasterRepository.save(projectMaster);

        return ProjectResponse.fromEntity(projectMasterSaved);
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        User user = userService.getCurrentUser();
        Optional<ProjectMaster> optionalProject = projectMasterRepository.findByIdAndUser(id, user);

        ProjectMaster projectMaster = optionalProject
                .orElseThrow(() -> new ProjectNotFoundException("Project not found for this user"));

        return ProjectResponse.fromEntity(projectMaster);
    }

    @Override
    public ProjectResponse updateProjectById(Long id, ProjectUpdateRequest projectRequest) {
        User user = userService.getCurrentUser();
        Optional<ProjectMaster> optionalProject = projectMasterRepository.findByIdAndUser(id, user);

        ProjectMaster projectMaster = optionalProject
                .orElseThrow(() -> new ProjectNotFoundException("Project not found for this user"));

        if (projectMasterRepository.existsByUserAndNameAndIdNot(user, projectRequest.getName(), id)) {
            throw new DuplicateProjectException("You already have a project with this name");
        }

        if(projectRequest.getName()!= null && !projectRequest.getName().isBlank()) {
            projectMaster.setName(projectRequest.getName());
        }
        if(projectRequest.getDescription() != null && !projectRequest.getDescription().isBlank()) {
            projectMaster.setDescription(projectRequest.getDescription());
        }
        ProjectMaster projectMasterUpdated = projectMasterRepository.save(projectMaster);

        return ProjectResponse.fromEntity(projectMasterUpdated);
    }

    @Override
    public Page<ProjectResponse> searchProjects(String name, int page, int size, String sortedBy, String orderBy) {
        User user = userService.getCurrentUser();
        Sort sort = orderBy.equalsIgnoreCase("desc") ? Sort.by(sortedBy).descending() : Sort.by(sortedBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<ProjectMaster> projectMasterPage = projectMasterRepository.searchProjects(user.getId(),name,pageable);

        return projectMasterPage.map(ProjectResponse::fromEntity);
    }

    @Transactional
    @Override
    public String deleteProjectById(Long id) {
        User user = userService.getCurrentUser();

      int taskDeleted =  taskMasterRepository.deleteByProject(id,user.getId());

       int projectDeleted =  projectMasterRepository.deleteByIdAndUser(id,user);
        if (projectDeleted == 0) {
            throw new ProjectNotFoundException("Project not found for this user");
        }
        return "Deleted successfully !!";
    }
}
