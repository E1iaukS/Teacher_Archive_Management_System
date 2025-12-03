package com.example.teacherarchive.repository;

import com.example.teacherarchive.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Page<Teacher> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
