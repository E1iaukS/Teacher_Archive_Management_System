CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20),
    status TINYINT DEFAULT 1,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50),
    role_code VARCHAR(50),
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    role_id BIGINT,
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES sys_user(id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES sys_role(id)
);

CREATE TABLE IF NOT EXISTS sys_department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100),
    dept_code VARCHAR(50),
    parent_id BIGINT,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS teacher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    teacher_no VARCHAR(50) UNIQUE,
    name VARCHAR(50),
    gender TINYINT,
    birthday DATE,
    id_number_encrypted VARCHAR(512),
    phone_encrypted VARCHAR(512),
    email VARCHAR(100),
    hire_date DATE,
    dept_id BIGINT,
    title VARCHAR(100),
    employment_status TINYINT,
    address_encrypted VARCHAR(1024),
    emergency_contact_encrypted VARCHAR(1024),
    remark TEXT,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS teacher_archive (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    teacher_id BIGINT,
    archive_no VARCHAR(50),
    archive_location VARCHAR(255),
    archive_status TINYINT,
    archive_type VARCHAR(50),
    sensitive_content_encrypted LONGTEXT,
    remark TEXT,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS archive_flow_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    archive_id BIGINT,
    teacher_id BIGINT,
    flow_type TINYINT,
    from_location VARCHAR(255),
    to_location VARCHAR(255),
    handler_user_id BIGINT,
    reason VARCHAR(255),
    operated_at DATETIME,
    remark TEXT
);

CREATE TABLE IF NOT EXISTS teacher_resignation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    teacher_id BIGINT,
    resignation_date DATE,
    resignation_type VARCHAR(50),
    handover_status TINYINT,
    archive_disposition TINYINT,
    remark TEXT,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS sys_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(100),
    operation VARCHAR(100),
    ip VARCHAR(64),
    created_at DATETIME,
    detail TEXT
);

INSERT INTO sys_role (role_name, role_code, description) VALUES
('系统管理员', 'ROLE_ADMIN', 'Super admin') ON DUPLICATE KEY UPDATE role_name=VALUES(role_name);

INSERT INTO sys_user (username, password_hash, real_name, status, created_at)
VALUES ('admin', '$2a$10$7sS7pU3dpPJoPa8C7OWZCOhF2/ZeQJQCfQzM0RKZ77N8BINU3C5je', '超级管理员', 1, NOW())
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO sys_user_role (user_id, role_id)
SELECT u.id, r.id FROM sys_user u, sys_role r
WHERE u.username='admin' AND r.role_code='ROLE_ADMIN' AND NOT EXISTS (
    SELECT 1 FROM sys_user_role ur WHERE ur.user_id=u.id AND ur.role_id=r.id
);
