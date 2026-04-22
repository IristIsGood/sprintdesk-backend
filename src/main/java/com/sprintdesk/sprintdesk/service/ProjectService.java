package com.sprintdesk.sprintdesk.service;

import com.sprintdesk.sprintdesk.dto.request.CreateProjectRequest;
import com.sprintdesk.sprintdesk.dto.response.ProjectResponse;
import com.sprintdesk.sprintdesk.entity.Project;
import com.sprintdesk.sprintdesk.entity.User;
import com.sprintdesk.sprintdesk.exception.ResourceNotFoundException;
import com.sprintdesk.sprintdesk.repository.ProjectRepository;
import com.sprintdesk.sprintdesk.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @CacheEvict(value = "projects", allEntries = true)  // 创建后清除缓存
    public ProjectResponse createProject(String ownerEmail,
                                         CreateProjectRequest request) {
        User owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(owner);
        projectRepository.save(project);

        return toResponse(project);
    }

    public List<ProjectResponse> getMyProjects(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return projectRepository.findByOwnerIdWithOwner(owner.getId())
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Cacheable(value = "projects", key = "#id")  // 缓存单个项目详情
    public ProjectResponse getProject(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return toResponse(project);
    }

    @Transactional
    @CacheEvict(value = "projects", key = "#id")  // 更新后清除该项目缓存
    public ProjectResponse updateProject(Long id, CreateProjectRequest request) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return toResponse(project);
    }

    @Transactional
    @CacheEvict(value = "projects", key = "#id")  // 删除后清除该项目缓存
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found");
        }
        projectRepository.deleteById(id);
    }

    private ProjectResponse toResponse(Project project) {
    String createdAt = project.getCreatedAt() != null
        ? project.getCreatedAt().toString()
        : "";

    return new ProjectResponse(
        project.getId(),
        project.getName(),
        project.getDescription(),
        project.getOwner().getEmail(),
        createdAt
    );
    }
}