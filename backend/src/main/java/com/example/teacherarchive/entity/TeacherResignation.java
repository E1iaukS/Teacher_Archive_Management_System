package com.example.teacherarchive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_resignation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherResignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long teacherId;
    private LocalDate resignationDate;
    private String resignationType;
    private Integer handoverStatus;
    private Integer archiveDisposition;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
