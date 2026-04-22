package com.sprintdesk.sprintdesk.repository;

import com.sprintdesk.sprintdesk.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // 分页查询某个项目的所有任务
    Page<Task> findByProjectId(Long projectId, Pageable pageable);
}