package com.sprintdesk.sprintdesk.repository;

import com.sprintdesk.sprintdesk.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 普通查询 — 有 N+1 问题（先用这个，之后我们来修它）
    List<Project> findByOwnerId(Long ownerId);

    // 用 JOIN FETCH 修复 N+1 的版本
    @Query("SELECT p FROM Project p JOIN FETCH p.owner WHERE p.owner.id = :ownerId")
    List<Project> findByOwnerIdWithOwner(@Param("ownerId") Long ownerId);
}