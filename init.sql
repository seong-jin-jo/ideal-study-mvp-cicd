-- idealstudy 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS idealstudy;

-- 관리자 계정 생성
CREATE USER IF NOT EXISTS 'manager'@'%' IDENTIFIED BY 'manager';

-- 관리자 권한 부여
GRANT ALL PRIVILEGES ON idealstudy.* TO 'manager'@'%';

-- 권한 변경 사항 적용
FLUSH PRIVILEGES;

-- member 테이블 생성
CREATE TABLE IF NOT EXISTS member (
                                      user_id CHAR(36) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    phone_address VARCHAR(15),
    email VARCHAR(100) NOT NULL UNIQUE,
    sex ENUM('MALE', 'FEMALE') NOT NULL,
    referral_id CHAR(36),
    level INT DEFAULT 0,
    role ENUM('GUEST', 'STUDENT', 'TEACHER', 'PARENTS', 'ADMIN') NOT NULL,  -- 수정된 부분
    from_social TINYINT DEFAULT 0,
    init TINYINT DEFAULT 1,
    deleted TINYINT DEFAULT 0
    );

-- 멤버 데이터 삽입
INSERT INTO member (user_id, password, name, phone_address, email, sex, referral_id, level, role, from_social, init, deleted)
VALUES
    (UUID(), 'password1', '홍길동', '010-1234-5678', 'hongil@example.com', 'MALE', NULL, 1, 'GUEST', 0, 1, 0),
    (UUID(), 'password2', '김철수', '010-2345-6789', 'kim@example.com', 'MALE', NULL, 2, 'ADMIN', 0, 1, 0),
    (UUID(), 'password3', '이영희', '010-3456-7890', 'lee@example.com', 'FEMALE', NULL, 1, 'GUEST', 0, 1, 0),
    (UUID(), 'password4', '박지은', '010-4567-8901', 'park@example.com', 'FEMALE', NULL, 1, 'GUEST', 1, 1, 0),
    (UUID(), 'password5', '최민호', '010-5678-9012', 'choi@example.com', 'MALE', NULL, 2, 'ADMIN', 0, 1, 0),
    (UUID(), 'password6', '정지훈', '010-6789-0123', 'jung@example.com', 'MALE', NULL, 1, 'STUDENT', 1, 1, 0),
    (UUID(), 'password7', '이수지', '010-7890-1234', 'lee2@example.com', 'FEMALE', NULL, 1, 'STUDENT', 0, 1, 0),
    (UUID(), 'password8', '김태희', '010-8901-2345', 'kim2@example.com', 'FEMALE', NULL, 2, 'ADMIN', 0, 1, 0),
    (UUID(), 'password9', '송유진', '010-9012-3456', 'song@example.com', 'FEMALE', NULL, 1, 'STUDENT', 1, 1, 0),
    (UUID(), 'password10', '정유미', '010-0123-4567', 'jung2@example.com', 'FEMALE', NULL, 1, 'STUDENT', 0, 1, 0),
    (UUID(), 'password11', '황성진', '010-1234-5679', 'hwang@example.com', 'MALE', NULL, 1, 'STUDENT', 0, 1, 0),
    (UUID(), 'password12', '김영수', '010-2345-6789', 'kim3@example.com', 'MALE', NULL, 1, 'STUDENT', 1, 1, 0),
    (UUID(), 'password13', '이찬희', '010-3456-7890', 'lee3@example.com', 'FEMALE', NULL, 2, 'ADMIN', 0, 1, 0),
    (UUID(), 'password14', '박상희', '010-4567-8901', 'park2@example.com', 'FEMALE', NULL, 1, 'STUDENT', 1, 1, 0),
    (UUID(), 'password15', '김지훈', '010-5678-9012', 'kim4@example.com', 'MALE', NULL, 1, 'STUDENT', 0, 1, 0),
    (UUID(), 'password16', '홍성호', '010-6789-0123', 'hong2@example.com', 'MALE', NULL, 2, 'ADMIN', 1, 1, 0),
    (UUID(), 'password17', '이정은', '010-7890-1234', 'lee4@example.com', 'FEMALE', NULL, 1, 'STUDENT', 0, 1, 0),
    (UUID(), 'password18', '김혜수', '010-8901-2345', 'kim5@example.com', 'FEMALE', NULL, 2, 'ADMIN', 0, 1, 0),
    (UUID(), 'password19', '정지민', '010-9012-3456', 'jung3@example.com', 'FEMALE', NULL, 1, 'STUDENT', 1, 1, 0),
    (UUID(), 'password20', '송윤호', '010-0123-4567', 'song2@example.com', 'MALE', NULL, 1, 'STUDENT', 0, 1, 0);