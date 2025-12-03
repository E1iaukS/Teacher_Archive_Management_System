package com.example.teacherarchive.service;

import com.example.teacherarchive.dto.ArchiveFlowRequest;
import com.example.teacherarchive.entity.ArchiveFlowRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveFlowService {
    Page<ArchiveFlowRecord> list(Pageable pageable);
    ArchiveFlowRecord create(ArchiveFlowRequest request);
    ArchiveFlowRecord find(Long id);
}
