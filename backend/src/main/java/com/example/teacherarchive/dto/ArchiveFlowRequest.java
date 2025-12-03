package com.example.teacherarchive.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArchiveFlowRequest {
    @NotNull
    private Long archiveId;
    @NotNull
    private Long teacherId;
    @NotNull
    private Integer flowType;
    private String fromLocation;
    private String toLocation;
    private String reason;
    private LocalDateTime operatedAt;
    private String remark;
}
