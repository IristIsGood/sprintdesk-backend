package com.sprintdesk.sprintdesk.dto.response;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String ownerEmail;
    private String createdAt;

    public ProjectResponse() {}

    public ProjectResponse(Long id, String name, String description,
                           String ownerEmail, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerEmail = ownerEmail;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getCreatedAt() { return createdAt; }
}