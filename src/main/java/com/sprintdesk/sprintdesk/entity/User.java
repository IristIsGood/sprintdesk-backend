package com.sprintdesk.sprintdesk.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setRole(Role role) { this.role = role; }
}

// @Entity — 告诉 Hibernate 这个 Java 类对应数据库里的一张表。
// @Table(name = "users") — 指定表名。不写的话默认用类名，User 会变成 user，但 user 在 PostgreSQL 是保留字，会报错，所以明确写 users。
// @Id + @GeneratedValue(strategy = GenerationType.IDENTITY) — @Id 标记主键，IDENTITY 让数据库自动递增（对应 PostgreSQL 的 bigserial）。
// @Column(unique = true, nullable = false) — 加数据库约束，email 不能重复、不能为空。
// @Enumerated(EnumType.STRING) — 把枚举存成字符串 "USER" / "ADMIN"，而不是数字 0 / 1。存字符串可读性更好，加新枚举值也不会乱。
// @Data + @NoArgsConstructor — Lombok 注解，自动生成所有 getter、setter、toString、无参构造函数。不用自己手写。

// 面试官最常问的一个问题：

// "为什么用 @Enumerated(EnumType.STRING) 而不是 EnumType.ORDINAL？"
// 你的答案："ORDINAL 存数字，如果以后枚举值的顺序变了，数据库里的数据就对不上了。STRING 存文字，更安全更直观。"

// The "Interview-Ready" Answer
// "A common interview question is: 'Why use @Enumerated(EnumType.STRING) instead of EnumType.ORDINAL?'"
// Your Answer:
// "ORDINAL stores the index of the enum. If the order of the enum constants changes later, the existing data in the database will no longer match the code. STRING, on the other hand, stores the actual name, making it far more robust and readable."