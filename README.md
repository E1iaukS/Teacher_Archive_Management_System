# 教师档案管理系统 (Teacher Archive Management System)

该示例提供了基于 **Spring Boot + Vue3** 的前后端分离教师档案管理系统，包括登录鉴权、用户与角色管理、教师档案、档案流转、离职管理等核心模块，并内置字段级 AES-GCM 加密与 JWT 认证。

## 后端（backend）
- 技术栈：Spring Boot 3、Spring Security + JWT、Spring Data JPA、Hibernate Validator、Springdoc OpenAPI。
- 构建：Maven，JDK 17。
- 配置：`backend/src/main/resources/application.yml`，根据本地环境调整数据库、JWT 密钥、加密密钥（通过 `SPRING_ENCRYPT_KEY` 读取）。
- 数据库初始化：`backend/src/main/resources/schema.sql` 提供基础表结构与默认管理员（账号 `admin`/密码 `admin123`，登录后请修改）。
- 运行：
  ```bash
  cd backend
  mvn spring-boot:run
  ```
- 接口文档：启动后访问 `http://localhost:8080/swagger-ui/index.html`。

## 前端（frontend）
- 技术栈：Vue 3 + TypeScript、Vite、Pinia、Vue Router、Element Plus、Axios。
- 运行：
  ```bash
  cd frontend
  npm install
  npm run dev
  ```
- 默认请求后端地址 `http://localhost:8080`，可在 `src/utils/request.ts` 中调整。

## 目录结构
- `backend/`：后端源码（分层：controller/service/repository/entity/security 等）。
- `frontend/`：前端 SPA 页面（layouts/pages/api/store 等）。
- `README.md`：项目说明。

## 安全与加密
- 密码使用 BCrypt 存储。
- `EncryptUtil` + `EncryptConverter` 实现字段级 AES-256-GCM 加密，敏感字段（身份证、电话、地址等）入库自动加密，出库自动解密。
- 推荐部署在 HTTPS 环境，所有接口统一返回 `{code,message,data}`。

## 测试
- 示例单元测试：`EncryptUtilTest` 验证加解密流程。

## 其他
- 角色：`ROLE_ADMIN`、`ROLE_ARCHIVE`、`ROLE_TEACHER`，可通过用户管理接口维护。
- 运行前请确保 MySQL8 已创建数据库 `teacher_archive`。
