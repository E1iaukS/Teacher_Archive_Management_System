package com.example.teacherarchive.controller;

import com.example.teacherarchive.dto.TeacherRequest;
import com.example.teacherarchive.entity.Teacher;
import com.example.teacherarchive.entity.TeacherArchive;
import com.example.teacherarchive.service.TeacherService;
import com.example.teacherarchive.vo.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
    public ApiResponse<Page<Teacher>> list(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(required = false) String keyword) {
        return ApiResponse.success(teacherService.list(keyword, PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<Teacher> detail(@PathVariable Long id, Authentication auth) {
        return ApiResponse.success(teacherService.find(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
    public ApiResponse<Teacher> create(@RequestBody @Valid TeacherRequest request) {
        return ApiResponse.success(teacherService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
    public ApiResponse<Teacher> update(@PathVariable Long id, @RequestBody @Valid TeacherRequest request) {
        return ApiResponse.success(teacherService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/archive/{teacherId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
    public ApiResponse<TeacherArchive> archive(@PathVariable Long teacherId) {
        return ApiResponse.success(teacherService.getArchive(teacherId));
    }
}
