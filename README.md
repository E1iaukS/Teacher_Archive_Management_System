#教师档案管理系统（教师档案管理系统）

该示例提供了基于** Spring Boot + Vue3 **的前教师教师档案管理系统，包括登录鉴权、用户与角色管理、教师档案、档案流转、产权管理等核心模块，并内置字段级 AES-GCM 加密和 JWT 认证。

##工作室（后端）
-技术栈：Spring Boot 3、Spring Security + JWT、Spring Data JPA、Hibernate Validator、Springdoc OpenAPI。
-构建：Maven，JDK 17。
-配置：` backend/src/main/resources/application.yml `，根据本地环境调整数据库、JWT密钥、加密密钥（通过` SPRING_ENCRYPT_KEY `读取）。
-数据库初始化：` backend/src/main/resources/schema.sql `提供基础表结构与默认管理员（账号` admin ` /密码` admin123 `，登录后请修改）。
-运行：
  ``` bash
  CD后端
  mvn spring-boot:run
  ```
-接口文档：启动后访问` http://localhost:8080/swagger-ui/index.html `。

##前端（前端）
-技术栈：Vue 3 + TypeScript、Vite、Pinia、Vue Router、Element Plus、Axios。
-运行：
  ``` bash
  CD前端
  npm 安装
  npm run dev
  ```
-默认请求地址` http://localhost:8080`，可在` src /utils/request.ts`中调整。

##目录结构
-  ` backend/ `：源码（层次：controller/service/repository/entity/security 等）。
-  ` frontend/ `：前端SPA页面（layouts/pages/api/store 等）。
-  ` README.md `：项目说明。

##安全与加密
-密码使用 BCrypt 存储。
-  ` EncryptUtil ` + ` EncryptConverter `实现字段级 AES-256-GCM 加密，敏感字段（身份证、电话、地址等）入库自动加密，出库自动解密。
-推荐在部署HTTPS环境，所有接口统一返回` {code,message,data} `。

##测试
-示例单元测试：` EncryptUtilTest`验证加解密流程。

##其他
-角色：` ROLE_ADMIN `、` ROLE_ARCHIVE `、` ROLE_TEACHER `，可通过用户管理接口维护。
-运行前请确保MySQL8已创建数据库` teacher_archive` 。
