package com.sprintdesk.sprintdesk.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String ownerEmail;
    private String createdAt;

    public ProjectResponse() {}

    @JsonCreator
    public ProjectResponse(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("ownerEmail") String ownerEmail,
            @JsonProperty("createdAt") String createdAt) {
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