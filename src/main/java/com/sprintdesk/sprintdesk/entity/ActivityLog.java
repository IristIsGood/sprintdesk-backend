package com.sprintdesk.sprintdesk.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

// @Document 告诉 Spring 这个类存到 MongoDB
// collection = "activity_logs" 是 MongoDB 里的集合名（相当于 SQL 的表名）
@Document(collection = "activity_logs")
public class ActivityLog {

    // MongoDB 用 String 类型的 ID，不是 Long
    // 自动生成类似 "507f1f77bcf86cd799439011" 的 ObjectId
    @Id
    private String id;

    private Long userId;       // 谁做的操作
    private Long projectId;    // 哪个项目
    private String action;     // 做了什么，例如 "TASK_CREATED", "STATUS_CHANGED"

    // Map<String, Object> 是关键 — 不同 action 存不同字段
    // 创建任务: {"taskId": 1, "title": "Fix bug"}
    // 修改状态: {"taskId": 1, "oldStatus": "TODO", "newStatus": "DONE"}
    private Map<String, Object> details;

    private LocalDateTime timestamp;

    public ActivityLog() {}

    public ActivityLog(Long userId, Long projectId, String action,
                       Map<String, Object> details) {
        this.userId = userId;
        this.projectId = projectId;
        this.action = action;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getProjectId() { return projectId; }
    public String getAction() { return action; }
    public Map<String, Object> getDetails() { return details; }
    public LocalDateTime getTimestamp() { return timestamp; }
}