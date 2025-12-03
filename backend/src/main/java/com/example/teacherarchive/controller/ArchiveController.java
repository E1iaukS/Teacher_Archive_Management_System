package com.example.teacherarchive.controller;

import com.example.teacherarchive.entity.TeacherArchive;
import com.example.teacherarchive.repository.TeacherArchiveRepository;
import com.example.teacherarchive.vo.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/archives")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARCHIVE')")
public class ArchiveController {
    private final TeacherArchiveRepository archiveRepository;

    public ArchiveController(TeacherArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    @GetMapping
    public ApiResponse<Page<TeacherArchive>> list(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(archiveRepository.findAll(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<TeacherArchive> detail(@PathVariable Long id) {
        return ApiResponse.success(archiveRepository.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<TeacherArchive> create(@RequestBody TeacherArchive archive) {
        return ApiResponse.success(archiveRepository.save(archive));
    }

    @PutMapping("/{id}")
    public ApiResponse<TeacherArchive> update(@PathVariable Long id, @RequestBody TeacherArchive archive) {
        archive.setId(id);
        return ApiResponse.success(archiveRepository.save(archive));
    }
}
