package com.example.teacherarchive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_operation_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String module;
    private String operation;
    private String ip;
    private LocalDateTime createdAt;
    private String detail;
}
