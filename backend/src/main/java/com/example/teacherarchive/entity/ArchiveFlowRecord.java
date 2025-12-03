package com.example.teacherarchive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "archive_flow_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArchiveFlowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long archiveId;
    private Long teacherId;
    private Integer flowType;
    private String fromLocation;
    private String toLocation;
    private Long handlerUserId;
    private String reason;
    private LocalDateTime operatedAt;
    private String remark;
}
