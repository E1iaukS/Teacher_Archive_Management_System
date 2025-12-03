package com.example.teacherarchive.entity;

import com.example.teacherarchive.util.EncryptConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_archive")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long teacherId;
    private String archiveNo;
    private String archiveLocation;
    private Integer archiveStatus;
    private String archiveType;

    @Convert(converter = EncryptConverter.class)
    private String sensitiveContentEncrypted;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
