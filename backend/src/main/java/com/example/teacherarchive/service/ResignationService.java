package com.example.teacherarchive.service;

import com.example.teacherarchive.dto.ResignationRequest;
import com.example.teacherarchive.entity.TeacherResignation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResignationService {
    Page<TeacherResignation> list(Pageable pageable);
    TeacherResignation create(ResignationRequest request);
    TeacherResignation update(Long id, ResignationRequest request);
    TeacherResignation find(Long id);
}
