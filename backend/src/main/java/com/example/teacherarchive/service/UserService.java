package com.example.teacherarchive.service;

import com.example.teacherarchive.dto.UserRequest;
import com.example.teacherarchive.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Page<SysUser> list(String keyword, Pageable pageable);
    SysUser create(UserRequest request);
    SysUser update(Long id, UserRequest request);
    void delete(Long id);
    Optional<SysUser> findById(Long id);
}
