package com.sprintdesk.sprintdesk.controller;

import com.sprintdesk.sprintdesk.entity.ActivityLog;
import com.sprintdesk.sprintdesk.service.ActivityLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// 提供一个接口查看某个项目的操作历史
@RestController
@RequestMapping("/api/v1/projects")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    // GET /api/v1/projects/{id}/logs → 查这个项目的所有操作记录
    @GetMapping("/{id}/logs")
    public ResponseEntity<List<ActivityLog>> getProjectLogs(@PathVariable Long id) {
        return ResponseEntity.ok(activityLogService.getProjectLogs(id));
    }
}