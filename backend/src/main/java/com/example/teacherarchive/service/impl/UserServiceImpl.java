package com.example.teacherarchive.service.impl;

import com.example.teacherarchive.dto.UserRequest;
import com.example.teacherarchive.entity.SysRole;
import com.example.teacherarchive.entity.SysUser;
import com.example.teacherarchive.repository.SysRoleRepository;
import com.example.teacherarchive.repository.SysUserRepository;
import com.example.teacherarchive.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final SysUserRepository userRepository;
    private final SysRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(SysUserRepository userRepository, SysRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<SysUser> list(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findAll(pageable).map(u -> u); // simplified
    }

    @Override
    public SysUser create(UserRequest request) {
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setRealName(request.getRealName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus());
        user.setCreatedAt(LocalDateTime.now());
        if (request.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        Set<SysRole> roles = request.getRoleCodes().stream()
                .map(code -> roleRepository.findByRoleCode(code).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public SysUser update(Long id, UserRequest request) {
        SysUser user = userRepository.findById(id).orElseThrow();
        user.setRealName(request.getRealName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        Set<SysRole> roles = request.getRoleCodes().stream()
                .map(code -> roleRepository.findByRoleCode(code).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<SysUser> findById(Long id) {
        return userRepository.findById(id);
    }
}
