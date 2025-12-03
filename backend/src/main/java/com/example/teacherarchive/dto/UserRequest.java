package com.example.teacherarchive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String realName;
    @Email
    private String email;
    private String phone;
    private Integer status = 1;
    @NotEmpty
    private Set<String> roleCodes;
    private String password;
}
