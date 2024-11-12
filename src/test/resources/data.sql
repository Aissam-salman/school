-- Suppression des tables dans l'ordre correct
DROP TABLE IF EXISTS Rate CASCADE;
DROP TABLE IF EXISTS student_course CASCADE;
DROP TABLE IF EXISTS Student CASCADE;
DROP TABLE IF EXISTS Course CASCADE;

-- Création de la table Course
CREATE TABLE IF NOT EXISTS Course
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    code VARCHAR(50) UNIQUE NOT NULL
);

-- Création de la table Student
CREATE TABLE IF NOT EXISTS Student
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

-- Création de la table d'association entre Student et Course
CREATE TABLE IF NOT EXISTS student_course
(
    student_id INT,
    course_id  INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Student (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Course (id) ON DELETE CASCADE
);

-- Création de la table Rate avec date
CREATE TABLE IF NOT EXISTS Rate
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    course_id  INT,
    student_id INT,
    rate_v     DOUBLE CHECK (rate_v >= 0 AND rate_v <= 20),
    rate_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES Course (id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES Student (id) ON DELETE CASCADE
);

-- Insertion des données de test avec MERGE
MERGE INTO Course (name, code)
    KEY (code)
    VALUES ('Mathematics', 'MATH101');

MERGE INTO Course (name, code)
    KEY (code)
    VALUES ('Physics', 'PHYS201');

MERGE INTO Course (name, code)
    KEY (code)
    VALUES ('Chemistry', 'CHEM301');

-- Insertion des étudiants
INSERT INTO Student (first_name, last_name)
VALUES ('Alice', 'Martin');
INSERT INTO Student (first_name, last_name)
VALUES ('Bob', 'Dupont');
INSERT INTO Student (first_name, last_name)
VALUES ('Charlie', 'Durand');

-- Inscriptions aux cours de maths
INSERT INTO student_course (student_id, course_id)
VALUES (1, 1);
INSERT INTO student_course (student_id, course_id)
VALUES (2, 1);
INSERT INTO student_course (student_id, course_id)
VALUES (3, 1);

-- Notes directes (sans SELECT complexe)
INSERT INTO Rate (course_id, student_id, rate_v, rate_date)
VALUES (1, 1, 19.8, '2024-01-15 10:00:00');

INSERT INTO Rate (course_id, student_id, rate_v, rate_date)
VALUES (1, 1, 18.5, '2024-02-15 10:00:00');

INSERT INTO Rate (course_id, student_id, rate_v, rate_date)
VALUES (1, 2, 15.0, '2024-01-15 10:00:00');

INSERT INTO Rate (course_id, student_id, rate_v, rate_date)
VALUES (1, 3, 7.5, '2024-01-15 10:00:00');