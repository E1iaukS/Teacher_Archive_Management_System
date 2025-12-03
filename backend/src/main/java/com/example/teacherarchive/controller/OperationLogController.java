package com.example.teacherarchive.controller;

import com.example.teacherarchive.entity.OperationLog;
import com.example.teacherarchive.repository.OperationLogRepository;
import com.example.teacherarchive.vo.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class OperationLogController {
    private final OperationLogRepository repository;

    public OperationLogController(OperationLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<Page<OperationLog>> list(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(repository.findAll(PageRequest.of(page, size)));
    }
}
