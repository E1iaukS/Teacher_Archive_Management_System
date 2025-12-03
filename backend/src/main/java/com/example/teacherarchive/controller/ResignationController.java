package com.example.teacherarchive.controller;

import com.example.teacherarchive.dto.ResignationRequest;
import com.example.teacherarchive.entity.TeacherResignation;
import com.example.teacherarchive.service.ResignationService;
import com.example.teacherarchive.vo.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resignations")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
public class ResignationController {
    private final ResignationService resignationService;

    public ResignationController(ResignationService resignationService) {
        this.resignationService = resignationService;
    }

    @GetMapping
    public ApiResponse<Page<TeacherResignation>> list(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(resignationService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<TeacherResignation> detail(@PathVariable Long id) {
        return ApiResponse.success(resignationService.find(id));
    }

    @PostMapping
    public ApiResponse<TeacherResignation> create(@RequestBody @Valid ResignationRequest request) {
        return ApiResponse.success(resignationService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TeacherResignation> update(@PathVariable Long id, @RequestBody @Valid ResignationRequest request) {
        return ApiResponse.success(resignationService.update(id, request));
    }
}
