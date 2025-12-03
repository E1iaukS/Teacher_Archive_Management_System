package com.example.teacherarchive.service.impl;

import com.example.teacherarchive.dto.ResignationRequest;
import com.example.teacherarchive.entity.Teacher;
import com.example.teacherarchive.entity.TeacherResignation;
import com.example.teacherarchive.repository.TeacherArchiveRepository;
import com.example.teacherarchive.repository.TeacherRepository;
import com.example.teacherarchive.repository.TeacherResignationRepository;
import com.example.teacherarchive.service.ResignationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ResignationServiceImpl implements ResignationService {

    private final TeacherResignationRepository resignationRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherArchiveRepository archiveRepository;

    public ResignationServiceImpl(TeacherResignationRepository resignationRepository, TeacherRepository teacherRepository, TeacherArchiveRepository archiveRepository) {
        this.resignationRepository = resignationRepository;
        this.teacherRepository = teacherRepository;
        this.archiveRepository = archiveRepository;
    }

    @Override
    public Page<TeacherResignation> list(Pageable pageable) {
        return resignationRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public TeacherResignation create(ResignationRequest request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId()).orElseThrow();
        teacher.setEmploymentStatus(2); // 离职
        teacherRepository.save(teacher);

        resignationRepository.findById(request.getTeacherId());
        TeacherResignation record = new TeacherResignation();
        applyRequest(record, request);
        record.setCreatedAt(LocalDateTime.now());
        TeacherResignation saved = resignationRepository.save(record);

        archiveRepository.findByTeacherId(teacher.getId()).ifPresent(archive -> {
            archive.setArchiveStatus(request.getArchiveDisposition());
            archive.setUpdatedAt(LocalDateTime.now());
            archiveRepository.save(archive);
        });
        return saved;
    }

    @Override
    public TeacherResignation update(Long id, ResignationRequest request) {
        TeacherResignation record = resignationRepository.findById(id).orElseThrow();
        applyRequest(record, request);
        record.setUpdatedAt(LocalDateTime.now());
        return resignationRepository.save(record);
    }

    @Override
    public TeacherResignation find(Long id) {
        return resignationRepository.findById(id).orElseThrow();
    }

    private void applyRequest(TeacherResignation record, ResignationRequest request) {
        record.setTeacherId(request.getTeacherId());
        record.setResignationDate(request.getResignationDate());
        record.setResignationType(request.getResignationType());
        record.setHandoverStatus(request.getHandoverStatus());
        record.setArchiveDisposition(request.getArchiveDisposition());
        record.setRemark(request.getRemark());
    }
}
