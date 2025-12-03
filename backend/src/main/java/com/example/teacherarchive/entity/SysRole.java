package com.example.teacherarchive.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sys_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;
    private String roleCode;
    private String description;
}
