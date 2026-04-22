package com.sprintdesk.sprintdesk.dto.response;

import java.time.LocalDateTime;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long projectId;
    private String createdAt;

    public TaskResponse(Long id, String title, String description,
                        String status, String priority,
                        Long projectId, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.projectId = projectId;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public Long getProjectId() { return projectId; }
    public String getCreatedAt() { return createdAt; }
}