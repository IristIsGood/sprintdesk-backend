package com.sprintdesk.sprintdesk.service;

import com.sprintdesk.sprintdesk.entity.ActivityLog;
import com.sprintdesk.sprintdesk.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    // 记录一条操作日志
    // 用 Map<String, Object> 存 details，灵活，不需要固定 schema
    public void log(Long userId, Long projectId,
                    String action, Map<String, Object> details) {
        ActivityLog log = new ActivityLog(userId, projectId, action, details);
        activityLogRepository.save(log);
    }

    // 查某个项目的操作历史
    public List<ActivityLog> getProjectLogs(Long projectId) {
        return activityLogRepository.findByProjectIdOrderByTimestampDesc(projectId);
    }
}