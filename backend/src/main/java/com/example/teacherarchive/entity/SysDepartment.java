package com.example.teacherarchive.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sys_department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deptName;
    private String deptCode;
    private Long parentId;
    private String description;
}
