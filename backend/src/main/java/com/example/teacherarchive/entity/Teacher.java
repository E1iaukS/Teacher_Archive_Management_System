package com.example.teacherarchive.entity;

import com.example.teacherarchive.util.EncryptConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String teacherNo;
    private String name;
    private Integer gender;
    private LocalDate birthday;

    @Convert(converter = EncryptConverter.class)
    private String idNumberEncrypted;
    @Convert(converter = EncryptConverter.class)
    private String phoneEncrypted;
    private String email;
    private LocalDate hireDate;
    private Long deptId;
    private String title;
    private Integer employmentStatus;

    @Convert(converter = EncryptConverter.class)
    private String addressEncrypted;
    @Convert(converter = EncryptConverter.class)
    private String emergencyContactEncrypted;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
