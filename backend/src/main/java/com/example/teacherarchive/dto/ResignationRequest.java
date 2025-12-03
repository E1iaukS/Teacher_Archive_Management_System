package com.example.teacherarchive.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResignationRequest {
    @NotNull
    private Long teacherId;
    private LocalDate resignationDate;
    private String resignationType;
    private Integer handoverStatus;
    private Integer archiveDisposition;
    private String remark;
}
