package com.example.teacherarchive.service.impl;

import com.example.teacherarchive.dto.TeacherRequest;
import com.example.teacherarchive.entity.Teacher;
import com.example.teacherarchive.entity.TeacherArchive;
import com.example.teacherarchive.repository.TeacherArchiveRepository;
import com.example.teacherarchive.repository.TeacherRepository;
import com.example.teacherarchive.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherArchiveRepository archiveRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherArchiveRepository archiveRepository) {
        this.teacherRepository = teacherRepository;
        this.archiveRepository = archiveRepository;
    }

    @Override
    public Page<Teacher> list(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return teacherRepository.findAll(pageable);
        }
        return teacherRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    @Transactional
    public Teacher create(TeacherRequest request) {
        Teacher teacher = buildTeacherFromRequest(new Teacher(), request);
        teacher.setCreatedAt(LocalDateTime.now());
        Teacher saved = teacherRepository.save(teacher);
        TeacherArchive archive = buildArchiveFromRequest(new TeacherArchive(), request);
        archive.setTeacherId(saved.getId());
        archive.setCreatedAt(LocalDateTime.now());
        archiveRepository.save(archive);
        return saved;
    }

    @Override
    @Transactional
    public Teacher update(Long id, TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        buildTeacherFromRequest(teacher, request);
        teacher.setUpdatedAt(LocalDateTime.now());
        Teacher saved = teacherRepository.save(teacher);
        TeacherArchive archive = archiveRepository.findByTeacherId(id).orElse(new TeacherArchive());
        archive.setTeacherId(id);
        buildArchiveFromRequest(archive, request);
        archive.setUpdatedAt(LocalDateTime.now());
        archiveRepository.save(archive);
        return saved;
    }

    @Override
    public void delete(Long id) {
        teacherRepository.deleteById(id);
        archiveRepository.findByTeacherId(id).ifPresent(archiveRepository::delete);
    }

    @Override
    public TeacherArchive getArchive(Long teacherId) {
        return archiveRepository.findByTeacherId(teacherId).orElse(null);
    }

    @Override
    public Teacher find(Long id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    private Teacher buildTeacherFromRequest(Teacher teacher, TeacherRequest request) {
        teacher.setTeacherNo(request.getTeacherNo());
        teacher.setName(request.getName());
        teacher.setGender(request.getGender());
        teacher.setBirthday(request.getBirthday());
        teacher.setIdNumberEncrypted(request.getIdNumber());
        teacher.setPhoneEncrypted(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setHireDate(request.getHireDate());
        teacher.setDeptId(request.getDeptId());
        teacher.setTitle(request.getTitle());
        teacher.setEmploymentStatus(request.getEmploymentStatus());
        teacher.setAddressEncrypted(request.getAddress());
        teacher.setEmergencyContactEncrypted(request.getEmergencyContact());
        teacher.setRemark(request.getRemark());
        return teacher;
    }

    private TeacherArchive buildArchiveFromRequest(TeacherArchive archive, TeacherRequest request) {
        archive.setArchiveNo(request.getArchiveNo());
        archive.setArchiveLocation(request.getArchiveLocation());
        archive.setArchiveStatus(request.getArchiveStatus());
        archive.setArchiveType(request.getArchiveType());
        archive.setSensitiveContentEncrypted(request.getSensitiveContent());
        archive.setRemark(request.getRemark());
        return archive;
    }
}
