package com.sprintdesk.sprintdesk.service;

import com.sprintdesk.sprintdesk.dto.request.CreateTaskRequest;
import com.sprintdesk.sprintdesk.dto.response.TaskResponse;
import com.sprintdesk.sprintdesk.entity.Priority;
import com.sprintdesk.sprintdesk.entity.Task;
import com.sprintdesk.sprintdesk.entity.TaskStatus;
import com.sprintdesk.sprintdesk.exception.ResourceNotFoundException;
import com.sprintdesk.sprintdesk.repository.ProjectRepository;
import com.sprintdesk.sprintdesk.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ActivityLogService activityLogService; // ← 注入日志服务

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       ActivityLogService activityLogService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.activityLogService = activityLogService;
    }

    @Transactional
    public TaskResponse createTask(Long projectId, CreateTaskRequest request) {
        var project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setProject(project);

        if (request.getPriority() != null) {
            task.setPriority(Priority.valueOf(request.getPriority().toUpperCase()));
        }

        taskRepository.save(task);

        // 任务创建后记录日志到 MongoDB
        // Map.of() 创建不可变 Map，存这次操作的具体信息
        activityLogService.log(
            null,                          // userId 暂时传 null，之后接入登录用户
            projectId,
            "TASK_CREATED",
            Map.of(
                "taskId", task.getId(),
                "title", task.getTitle(),
                "priority", task.getPriority().name()
            )
        );

        return toResponse(task);
    }

    @Transactional
    public TaskResponse updateStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // 记录状态变化前后的值
        String oldStatus = task.getStatus().name();
        task.setStatus(TaskStatus.valueOf(status.toUpperCase()));

        // 状态改变后记录日志
        activityLogService.log(
            null,
            task.getProject().getId(),
            "STATUS_CHANGED",
            Map.of(
                "taskId", taskId,
                "oldStatus", oldStatus,
                "newStatus", status.toUpperCase()
            )
        );

        return toResponse(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // 删除前先记录日志（删除后就拿不到数据了）
        activityLogService.log(
            null,
            task.getProject().getId(),
            "TASK_DELETED",
            Map.of(
                "taskId", id,
                "title", task.getTitle()
            )
        );

        taskRepository.deleteById(id);
    }

    public org.springframework.data.domain.Page<TaskResponse> getTasksByProject(
            Long projectId,
            org.springframework.data.domain.Pageable pageable) {
        return taskRepository.findByProjectId(projectId, pageable)
            .map(this::toResponse);
    }

    private TaskResponse toResponse(Task task) {
        String createdAt = task.getCreatedAt() != null
            ? task.getCreatedAt().toString()
            : "";

        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus().name(),
            task.getPriority().name(),
            task.getProject().getId(),
            createdAt
        );
    }
}