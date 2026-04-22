package com.sprintdesk.sprintdesk.repository;

import com.sprintdesk.sprintdesk.entity.ActivityLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// MongoRepository 和 JpaRepository 用法一样
// 第一个泛型是 Document 类型，第二个是 ID 类型（MongoDB 用 String）
@Repository
public interface ActivityLogRepository extends MongoRepository<ActivityLog, String> {

    // 查某个项目的所有操作日志，按时间倒序
    List<ActivityLog> findByProjectIdOrderByTimestampDesc(Long projectId);

    // 查某个用户的所有操作记录
    List<ActivityLog> findByUserIdOrderByTimestampDesc(Long userId);
}