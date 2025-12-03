package com.example.teacherarchive.controller;

import com.example.teacherarchive.entity.SysDepartment;
import com.example.teacherarchive.repository.SysDepartmentRepository;
import com.example.teacherarchive.vo.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final SysDepartmentRepository repository;

    public DepartmentController(SysDepartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<List<SysDepartment>> list() {
        return ApiResponse.success(repository.findAll());
    }
}
