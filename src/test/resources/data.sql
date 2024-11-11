CREATE TABLE IF NOT EXISTS Course
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    code VARCHAR(50) UNIQUE NOT NULL
);


INSERT INTO Course (name, code)
VALUES ('Mathematics', 'MATH101');
INSERT INTO Course (name, code)
VALUES ('Physics', 'PHYS201');
INSERT INTO Course (name, code)
VALUES ('Chemistry', 'CHEM301');
