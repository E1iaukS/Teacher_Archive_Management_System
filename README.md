# 教师档案管理系统 (Teacher Archive Management System)

本项目提供基于 **Spring Boot 3 + Vue 3** 的前后端分离教师档案管理系统，涵盖登录鉴权、用户/角色管理、教师档案、档案流转、离职管理等核心模块，并内置字段级 AES-GCM 加密与 JWT 认证。以下为完整的本地运行与使用指南。

## 一、运行前准备
- **运行环境**：JDK 17、Node.js 18+、npm 9+、MySQL 8+。
- **创建数据库**：在 MySQL 中创建数据库，例如 `teacher_archive`，并为应用预留账号（具备 DDL/DML 权限）。
- **后端配置文件**：位于 `backend/src/main/resources/application.yml`，需要根据本地环境修改：
  - `spring.datasource.url`、`username`、`password` 对应上一步创建的数据库。
  - `security.jwt.secret` 自定义 JWT 密钥。
  - `security.encrypt-key` 读取环境变量 `SPRING_ENCRYPT_KEY`，用于 AES-GCM 字段加密，请在运行前导出：
    ```bash
    export SPRING_ENCRYPT_KEY="your-32-bytes-secret-key"
    ```
    **务必使用 32 字节密钥，保持安全性，不要提交到版本库。**

## 二、初始化数据库
项目提供 `schema.sql` 用于初始化表结构和基础数据（含默认管理员账号 `admin`/`admin123`，首次登录请尽快修改密码）：
```bash
cd backend
# 确保 application.yml 的数据源配置正确
mvn -DskipTests clean package
# 初始化表结构（会自动执行 schema.sql，也可以手动运行）
mysql -u<user> -p<pass> < teacher_archive < src/main/resources/schema.sql
```

## 三、启动后端服务
```bash
cd backend
# 以开发模式启动
mvn spring-boot:run
# 默认监听 http://localhost:8080
```
- 启动后可访问接口文档：`http://localhost:8080/swagger-ui/index.html`。
- 日志输出、数据源、JWT 与加密配置均可在 `application.yml` 中按需调整。

## 四、启动前端（SPA）
```bash
cd frontend
npm install
# 如需修改后端地址，可在 src/utils/request.ts 或 .env.* 文件中设置 VITE_API_BASE
npm run dev
# 默认在 http://localhost:5173 运行
```
- 登录页使用卡片式风格，输入用户名/密码后可进入系统。
- 前端会自动在请求头添加 JWT（存于 Pinia 状态），401/403 会统一提示。

## 五、核心功能与角色
- **角色**：`ROLE_ADMIN`（管理全部）、`ROLE_ARCHIVE`（档案管理员）、`ROLE_TEACHER`（仅查看本人基础信息）。
- **模块**：仪表盘、用户管理、教师信息、档案信息、档案流转、离职管理、系统日志（可选）。
- **敏感字段加密**：身份证/电话/地址/紧急联系人等通过 `EncryptUtil` + `EncryptConverter` 自动加解密，数据库中存储密文，接口返回明文（需具备权限）。

## 六、常用接口（示例）
- **认证**：`POST /api/auth/login`（用户名 + 密码获取 JWT）、`GET /api/auth/profile`（当前用户信息）。
- **用户管理（ADMIN）**：`GET /api/users`、`POST /api/users`、`PUT /api/users/{id}`、`DELETE /api/users/{id}`、`PUT /api/users/{id}/reset-password`。
- **教师档案**：`GET /api/teachers`、`GET /api/teachers/{id}`、`POST /api/teachers`、`PUT /api/teachers/{id}`、`DELETE /api/teachers/{id}`。
- **档案信息**：`GET /api/archives`、`GET /api/archives/{id}`、`POST /api/archives`、`PUT /api/archives/{id}`、`GET /api/archives/teacher/{teacherId}`。
- **档案流转**：`GET /api/archive-flows`、`GET /api/archive-flows/{id}`、`POST /api/archive-flows`（自动联动档案状态/位置）。
- **离职管理**：`GET /api/resignations`、`GET /api/resignations/{id}`、`POST /api/resignations`（同步更新教师状态与档案处置）、`PUT /api/resignations/{id}`。

## 七、测试与验证
- 后端示例单元测试：`mvn test`（包含 `EncryptUtilTest` 验证加解密流程）。
- 启动后可用默认管理员登录前端，尝试新增用户、录入教师、创建档案与流转记录来验证全链路。

## 八、部署建议
- 使用 HTTPS 提供前后端服务，避免明文传输凭据与敏感字段。
- 将 JWT 密钥与 AES 密钥存放在安全的配置中心或环境变量中，分环境管理。
- 数据库定期备份，敏感表可启用透明加密或磁盘加密，配合应用层字段加密。

## 九、目录结构速览
- `backend/`：Spring Boot 后端（controller/service/repository/entity/security 等分层）。
- `frontend/`：Vue 3 + Element Plus 前端（layouts/pages/api/store 等）。
- `README.md`：本使用说明。

## 十、默认账号
- 管理员：`admin` / `admin123`（登录后请修改）。
