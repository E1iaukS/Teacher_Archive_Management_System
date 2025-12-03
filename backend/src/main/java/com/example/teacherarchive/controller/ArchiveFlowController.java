package com.example.teacherarchive.controller;

import com.example.teacherarchive.dto.ArchiveFlowRequest;
import com.example.teacherarchive.entity.ArchiveFlowRecord;
import com.example.teacherarchive.service.ArchiveFlowService;
import com.example.teacherarchive.vo.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/archive-flows")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
public class ArchiveFlowController {
    private final ArchiveFlowService archiveFlowService;

    public ArchiveFlowController(ArchiveFlowService archiveFlowService) {
        this.archiveFlowService = archiveFlowService;
    }

    @GetMapping
    public ApiResponse<Page<ArchiveFlowRecord>> list(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(archiveFlowService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<ArchiveFlowRecord> detail(@PathVariable Long id) {
        return ApiResponse.success(archiveFlowService.find(id));
    }

    @PostMapping
    public ApiResponse<ArchiveFlowRecord> create(@RequestBody @Valid ArchiveFlowRequest request) {
        return ApiResponse.success(archiveFlowService.create(request));
    }
}
