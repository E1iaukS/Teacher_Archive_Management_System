package com.example.teacherarchive.repository;

import com.example.teacherarchive.entity.TeacherArchive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherArchiveRepository extends JpaRepository<TeacherArchive, Long> {
    Optional<TeacherArchive> findByTeacherId(Long teacherId);
}
