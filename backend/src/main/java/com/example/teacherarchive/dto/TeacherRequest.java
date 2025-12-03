package com.example.teacherarchive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherRequest {
    @NotBlank
    private String teacherNo;
    @NotBlank
    private String name;
    private Integer gender;
    private LocalDate birthday;
    private String idNumber;
    private String phone;
    private String email;
    private LocalDate hireDate;
    private Long deptId;
    private String title;
    private Integer employmentStatus;
    private String address;
    private String emergencyContact;
    private String remark;
    // Archive info
    private String archiveNo;
    private String archiveLocation;
    private Integer archiveStatus;
    private String archiveType;
    private String sensitiveContent;
}
