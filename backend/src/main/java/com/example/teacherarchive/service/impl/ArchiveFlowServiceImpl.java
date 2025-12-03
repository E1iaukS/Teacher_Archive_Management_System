package com.example.teacherarchive.service.impl;

import com.example.teacherarchive.dto.ArchiveFlowRequest;
import com.example.teacherarchive.entity.ArchiveFlowRecord;
import com.example.teacherarchive.entity.TeacherArchive;
import com.example.teacherarchive.repository.ArchiveFlowRecordRepository;
import com.example.teacherarchive.repository.TeacherArchiveRepository;
import com.example.teacherarchive.service.ArchiveFlowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ArchiveFlowServiceImpl implements ArchiveFlowService {

    private final ArchiveFlowRecordRepository flowRecordRepository;
    private final TeacherArchiveRepository archiveRepository;

    public ArchiveFlowServiceImpl(ArchiveFlowRecordRepository flowRecordRepository, TeacherArchiveRepository archiveRepository) {
        this.flowRecordRepository = flowRecordRepository;
        this.archiveRepository = archiveRepository;
    }

    @Override
    public Page<ArchiveFlowRecord> list(Pageable pageable) {
        return flowRecordRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ArchiveFlowRecord create(ArchiveFlowRequest request) {
        ArchiveFlowRecord record = new ArchiveFlowRecord();
        record.setArchiveId(request.getArchiveId());
        record.setTeacherId(request.getTeacherId());
        record.setFlowType(request.getFlowType());
        record.setFromLocation(request.getFromLocation());
        record.setToLocation(request.getToLocation());
        record.setReason(request.getReason());
        record.setOperatedAt(request.getOperatedAt() != null ? request.getOperatedAt() : LocalDateTime.now());
        record.setRemark(request.getRemark());
        ArchiveFlowRecord saved = flowRecordRepository.save(record);

        TeacherArchive archive = archiveRepository.findById(request.getArchiveId()).orElseThrow();
        archive.setArchiveStatus(request.getFlowType());
        archive.setArchiveLocation(request.getToLocation());
        archive.setUpdatedAt(LocalDateTime.now());
        archiveRepository.save(archive);
        return saved;
    }

    @Override
    public ArchiveFlowRecord find(Long id) {
        return flowRecordRepository.findById(id).orElseThrow();
    }
}
