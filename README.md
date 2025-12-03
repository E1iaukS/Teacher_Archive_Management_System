# MY_Code
你是一个资深 Java 全栈工程师，请按照下面的详细要求，实现一个“教师档案管理系统”（Teacher Archive Management System）。  

========================
一、总体目标
========================
实现一个 B/S 架构的教师档案管理系统，支持：
- 用户登录与权限控制（RBAC）；
- 用户管理（增删改查）；
- 教师档案信息管理（录入 / 修改 / 查询），部分敏感字段需要加密存储；
- 档案调入调出记录（档案流转日志）；
- 离职管理（离职教师档案处理）；

前后端分离：
- 后端：Java 技术栈，提供 RESTful API；
- 前端：单页应用（SPA），页面美观、现代化、响应式。

请给出完整可运行的项目结构与代码（包括后端、前端），并包含必要的注释和 README。

========================
二、技术栈与工程规范
========================

1. 后端技术栈（Java）
- JDK：17 或 11（推荐 17）
- 构建工具：Maven
- Web 框架：Spring Boot（最新稳定版本）
- 持久层：Spring Data JPA 或 MyBatis-Plus（二选一，任选你更熟悉的，保持写法统一）
- 安全认证：Spring Security + JWT
- 校验：Hibernate Validator（JSR-303 注解，如 @NotNull, @Size 等）
- 数据库：MySQL 8+
- 连接池：HikariCP（Spring Boot 默认）
- 日志：SLF4J + Logback
- 文档：Springdoc OpenAPI 或 Swagger3（自动生成接口文档）
- 加密：使用 JCE（如 AES-256-GCM）实现字段级加密；对密码使用 BCrypt 哈希。

2. 前端技术栈
- 框架：Vue 3 + TypeScript
- 构建工具：Vite
- 状态管理：Pinia
- 路由：Vue Router 4
- UI 组件库：Element Plus（要求整体风格美观、现代）
- 图标：Element Plus 自带图标或 iconfont
- 网络请求：Axios（封装请求拦截器、响应拦截器，自动携带 JWT）

3. 工程结构（示例）
- 后端（Maven 多模块可选，也可以单模块）：
  - com.example.teacher-archive
    - config
    - controller
    - service
    - service.impl
    - repository / mapper
    - entity
    - dto
    - vo
    - security
    - util
    - exception

- 前端：
  - src/
    - api/
    - components/
    - layouts/
    - pages/
    - router/
    - store/
    - styles/
    - utils/

请按上述结构组织代码，保证清晰分层：Controller → Service → Repository/Mapper → DB。

========================
三、业务角色与权限模型
========================

1. 用户角色
至少包含三类角色：
- 超级管理员（ROLE_ADMIN）
  - 管理所有用户和角色；
  - 管理所有教师档案和档案流转记录；
  - 查看、处理离职档案。
- 档案管理员 / 人事（ROLE_ARCHIVE）
  - 管理教师档案信息；
  - 新建/修改档案调入调出记录；
  - 管理离职教师档案。
- 普通教师用户（ROLE_TEACHER，可选）
  - 只能查看自己的基础档案信息（仅非敏感字段），不能修改。

2. 权限控制
- 使用 Spring Security + JWT：
  - 登录成功后，后端签发 JWT Token；
  - 前端将 Token 存储在安全位置（例如内存 + 刷新机制，避免长久存 localStorage）；
  - 所有需要授权的接口走 Bearer Token；
- 在 Controller 上使用 @PreAuthorize 或基于 URL 的角色权限控制：
  - 如 @PreAuthorize("hasRole('ADMIN')")。
- 提供统一的未登录 / 权限不足响应结构。

========================
四、数据库设计（MySQL）
========================

请设计并实现以下核心表（可以根据需要适当扩展字段）：

1. 用户与权限相关
(1) sys_user
- id (BIGINT, PK, auto_increment)
- username (VARCHAR(50), unique, not null)
- password_hash (VARCHAR(255), not null)     // BCrypt 存储
- real_name (VARCHAR(50))
- email (VARCHAR(100))
- phone (VARCHAR(20))
- status (TINYINT)  // 0=禁用, 1=启用
- created_at (DATETIME)
- updated_at (DATETIME)

(2) sys_role
- id (BIGINT, PK)
- role_name (VARCHAR(50))      // 显示名称，如“系统管理员”
- role_code (VARCHAR(50))      // 如 ROLE_ADMIN
- description (VARCHAR(255))

(3) sys_user_role （多对多映射）
- id (BIGINT, PK)
- user_id (BIGINT, FK -> sys_user)
- role_id (BIGINT, FK -> sys_role)

可选：sys_permission / sys_role_permission，如果你希望做更细粒度权限。

2. 教师与档案相关
(4) sys_department
- id (BIGINT, PK)
- dept_name (VARCHAR(100))
- dept_code (VARCHAR(50))
- parent_id (BIGINT)  // 上级部门，可为空
- description (VARCHAR(255))

(5) teacher
- id (BIGINT, PK)
- teacher_no (VARCHAR(50), unique)      // 工号
- name (VARCHAR(50))
- gender (TINYINT) // 0=未知,1=男,2=女
- birthday (DATE)
- id_number_encrypted (VARBINARY 或 VARCHAR(512))  // 身份证号，加密存储
- phone_encrypted (VARBINARY 或 VARCHAR(512))       // 电话，加密存储
- email (VARCHAR(100))
- hire_date (DATE)      // 入职日期
- dept_id (BIGINT, FK -> sys_department)
- title (VARCHAR(100))  // 职称，如“讲师”、“副教授”
- employment_status (TINYINT) // 1=在职,2=离职,3=退休
- address_encrypted (VARBINARY 或 VARCHAR(1024))    // 家庭地址，加密存储
- emergency_contact_encrypted (VARBINARY 或 VARCHAR(1024)) // 紧急联系人信息，加密存储
- remark (TEXT)
- created_at (DATETIME)
- updated_at (DATETIME)

(6) teacher_archive
- id (BIGINT, PK)
- teacher_id (BIGINT, FK -> teacher)
- archive_no (VARCHAR(50))         // 档案编号
- archive_location (VARCHAR(255))  // 档案存放地点（柜号、库房等）
- archive_status (TINYINT)         // 1=在库,2=借出,3=已转出,4=其他
- archive_type (VARCHAR(50))       // 人事档案/教学档案等
- sensitive_content_encrypted (LONGTEXT 或 VARBINARY)  // 简历、考核等敏感内容，加密存储（可存 JSON）
- remark (TEXT)
- created_at (DATETIME)
- updated_at (DATETIME)

(7) archive_flow_record（档案调入调出 / 借阅记录）
- id (BIGINT, PK)
- archive_id (BIGINT, FK -> teacher_archive)
- teacher_id (BIGINT, FK -> teacher)
- flow_type (TINYINT) // 1=调入,2=调出,3=借出,4=归还
- from_location (VARCHAR(255))
- to_location (VARCHAR(255))
- handler_user_id (BIGINT, FK -> sys_user) // 操作人
- reason (VARCHAR(255))
- operated_at (DATETIME)
- remark (TEXT)

(8) teacher_resignation（离职管理）
- id (BIGINT, PK)
- teacher_id (BIGINT, FK -> teacher)
- resignation_date (DATE)       // 离职日期
- resignation_type (VARCHAR(50)) // 主动离职、解聘、退休等
- handover_status (TINYINT)     // 0=未办理,1=办理中,2=已完成
- archive_disposition (TINYINT) // 1=保留,2=转出,3=销毁(逻辑),4=移交上级单位
- remark (TEXT)
- created_at (DATETIME)
- updated_at (DATETIME)

(9) sys_operation_log（可选，操作日志）
- id, user_id, operation, module, ip, created_at, detail 等。

请在实体类中映射上述字段，增加必要的索引（如 teacher_no、archive_no）。

========================
五、字段级加密与安全设计
========================

1. 密码存储
- 用户密码仅以 BCrypt 哈希形式存储，不可逆；
- 注册 / 重置密码时进行强度校验；

2. 字段级加密（Teacher & Archive 中的敏感字段）
需要加密存储的字段：
- teacher.id_number_encrypted
- teacher.phone_encrypted
- teacher.address_encrypted
- teacher.emergency_contact_encrypted
- teacher_archive.sensitive_content_encrypted

加密方案建议：
- 使用对称加密 AES-256-GCM；
- 密钥通过配置文件引用环境变量（如 SPRING_ENCRYPT_KEY），不要硬编码在代码中；
- 编写统一的加密工具类 EncryptUtil：
  - encrypt(String plainText) : String
  - decrypt(String cipherText) : String
- 在 JPA 中可使用 AttributeConverter，或在 MyBatis-Plus 中使用 TypeHandler，实现透明加解密：
  - 实体类读写字段时自动加解密；
  - 对外 VO/DTO 始终使用明文，持久化层自动处理密文。

3. 传输安全
- 在设计中说明推荐部署在 HTTPS 环境下；
- 后端返回统一的响应格式（code, message, data）。

========================
六、后端接口设计（RESTful）
========================

请为下列模块设计标准 REST API（路径仅为示例，可适当调整）：

1. 认证模块（AuthController）
- POST /api/auth/login
  - 请求：username, password
  - 返回：JWT token, 用户信息, 角色信息
- POST /api/auth/logout（可选）
- GET /api/auth/profile
  - 获取当前登录用户信息

2. 用户管理（仅 ADMIN 可用）
- GET /api/users
  - 支持分页、关键字搜索（用户名、真实姓名、邮箱）
- POST /api/users
  - 新增用户，指定角色
- PUT /api/users/{id}
  - 修改用户信息（角色、状态等）
- DELETE /api/users/{id}
- PUT /api/users/{id}/reset-password

3. 教师档案信息管理
- GET /api/teachers
  - 分页查询；支持条件查询：
    - teacher_no, name, dept_id, employment_status, hire_date 范围等
- GET /api/teachers/{id}
  - 返回教师详细信息（对敏感字段进行解密后再返回；注意权限控制：普通教师只能查看自己的基础信息，敏感字段可隐藏）
- POST /api/teachers
  - 新增教师；写入 teacher 与 teacher_archive 基础信息
- PUT /api/teachers/{id}
  - 修改教师信息
- DELETE /api/teachers/{id}
  - 建议采用逻辑删除（增加 deleted 标记）

4. 教师档案详细管理（Archive）
- GET /api/archives
  - 支持按 teacher_no, archive_no, dept, status 等过滤
- GET /api/archives/{id}
- POST /api/archives
- PUT /api/archives/{id}
- GET /api/archives/teacher/{teacherId}
  - 获取某教师档案信息

5. 档案调入调出记录（ArchiveFlow）
- GET /api/archive-flows
  - 支持按 teacher_no, archive_no, flow_type, 日期范围 查询
- GET /api/archive-flows/{id}
- POST /api/archive-flows
  - 新建一条档案流转记录；
  - 同时联动更新 teacher_archive.archive_status / archive_location
- 提供导出功能（可选）：导出为 Excel

6. 离职管理模块（Resignation）
- GET /api/resignations
  - 按部门、日期、类型筛选
- GET /api/resignations/{id}
- POST /api/resignations
  - 为某教师创建离职记录：
    - 自动更新 teacher.employment_status = 离职；
    - 根据 archive_disposition 字段决定档案状态（如转出、保留等），并记录一条 archive_flow_record；
- PUT /api/resignations/{id}
  - 更新交接状态、备注等。

7. 通用与审计
- GET /api/departments（部门列表）
- GET /api/logs（操作日志，ADMIN 查看）

所有接口：
- 使用统一响应格式：{ code: 0/非0, message: string, data: object }
- 对传入参数进行校验（Hibernate Validator），不合法时返回明确错误信息。

========================
七、前端 UI 设计与页面结构（美观要求）
========================

整体要求：
- 使用 Vue 3 + Element Plus，风格参考现代后台管理系统（如 Element Plus 官方示例、Arco Admin 风格）。
- 配色：浅色系主题，主色建议为蓝色系（如 #409EFF），辅色为灰 / 中性色。
- 布局：典型的“左侧菜单 + 顶部导航 + 主内容区域”后台界面。
- 响应式：在窄屏时侧边栏可折叠。

1. 页面布局
- Layout 结构：
  - 顶部 Header：系统名称（“教师档案管理系统”）、当前用户信息、退出登录、语言切换（可选）、主题切换（可选）。
  - 左侧 Sidebar：菜单导航（图标 + 文本），常驻或可折叠。
  - 主内容区域：Breadcrumb（面包屑导航） + 页面标题 + 内容卡片。

- 菜单结构示例：
  - 仪表盘（首页）
  - 用户与权限管理
    - 用户管理
    - 角色管理（可选）
  - 教师档案管理
    - 教师信息
    - 档案信息
  - 档案流转管理
    - 档案调入调出记录
  - 离职管理
    - 离职记录
  - 系统日志（可选）

2. 具体页面风格
- 登录页：
  - 居中卡片式登录框，背景可使用淡色渐变或简洁插画；
  - 表单项：用户名、密码、登录按钮、记住我（可选）；
  - 登录成功后跳转到仪表盘。

- 仪表盘（Dashboard）：
  - 展示系统概览：
    - 总教师人数、在职/离职人数；
    - 档案在库/借出/转出数量；
    - 最近档案流转记录简表；
    - 可加入简单的统计图（使用 ECharts 或 Element Plus + 任意图表库）。

- 列表页面（用户管理 / 教师列表 / 档案记录 / 离职记录）：
  - 使用 Element Plus 的 Table 组件；
  - 支持分页、排序（至少按时间）、关键字模糊搜索；
  - 顶部过滤区域使用 Form + 栅格布局（两三行条件，整齐对齐）；
  - 列表行操作按钮统一使用“查看 / 编辑 / 删除”图标按钮；
  - 敏感信息字段在列表中可局部打码展示（如“138****5678”）。

- 详情与编辑表单页：
  - 使用 Card + Form 布局，分组展示信息：
    - 基本信息（姓名、工号、部门、职称等）
    - 联系方式与个人信息（电话、地址等）
    - 档案信息（档案编号、存放位置、状态等）
  - 使用合理的间距（Element Plus 默认 + 适当的 margin/padding）；
  - 长表单分区块，使用标题和分割线分隔；
  - 使用下拉选择（Select）、日期组件（DatePicker）等控件，保持交互一致性；
  - 编辑时进行前端校验（必须字段高亮提示）。

- 离职管理页：
  - 列表 + 抽屉式/弹窗式“新增离职记录”；
  - 在新增离职时，可自动联动显示该教师当前档案状态，并通过提示说明离职后档案的处置。

3. 前端交互与体验
- 全局 Loading：请求期间在页面显示 Loading 动画；
- 统一错误提示：封装 Axios 拦截器，在 401/403/500 时统一提示；
- 成功操作使用 Element Plus 的 Message 或 Notification；
- 页面切换有简洁过渡动画（例如淡入淡出或滑动，不要太复杂）。

========================
八、代码质量与通用要求
========================

1. 代码规范
- 后端：遵循典型 Spring Boot 项目结构、命名规范；
- 使用 DTO/VO 分离实体与接口返回对象；
- Service 层不直接暴露 Entity 给 Controller，必要时进行转换；
- Controller 保持瘦，业务逻辑放在 Service 层。

2. 异常处理
- 编写全局异常处理器（如 @ControllerAdvice + @ExceptionHandler）；
- 捕获参数校验异常、业务异常、系统异常；
- 返回统一的错误响应结构。

3. 日志与审计
- 对登录、关键业务操作（新增/删除档案、离职处理、档案调出等）写入 sys_operation_log；
- 日志记录：操作用户、时间、IP、模块、操作类型、关键参数摘要。

4. 测试与文档
- 至少为核心 Service 编写部分单元测试（JUnit + Mockito 或 Spring Boot Test）；
- 提供 Swagger/OpenAPI 接口文档；
- 提供 README：
  - 开发环境要求；
  - 数据库初始化方法（提供 schema.sql 或 Flyway/Liquibase 脚本）；
  - 后端启动步骤；
  - 前端启动步骤；
  - 默认管理员账号/密码（如 admin / admin123，首次登录强制修改）。

========================
九、最终交付
========================

请输出：
1. 完整的后端项目代码（包含所有 Java 源文件、配置文件、构建脚本）；
2. 完整的前端项目代码（包含所有 Vue 组件、路由、状态管理、样式等）；
3. 数据库建表 SQL 脚本；
4. 保证项目可以通过简单配置（如修改 application.yml 中的数据库信息）即可在本地成功运行。

请严格按照以上要求实现“教师档案管理系统”的完整前后端代码。
