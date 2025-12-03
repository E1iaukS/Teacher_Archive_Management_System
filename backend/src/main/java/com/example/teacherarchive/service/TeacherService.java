package com.example.teacherarchive.service;

import com.example.teacherarchive.dto.TeacherRequest;
import com.example.teacherarchive.entity.Teacher;
import com.example.teacherarchive.entity.TeacherArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {
    Page<Teacher> list(String keyword, Pageable pageable);
    Teacher create(TeacherRequest request);
    Teacher update(Long id, TeacherRequest request);
    void delete(Long id);
    TeacherArchive getArchive(Long teacherId);
    Teacher find(Long id);
}
