DROP DATABASE IF EXISTS markwell_tester;
CREATE DATABASE IF NOT EXISTS markwell_tester;
USE markwell_tester;
CREATE TABLE IF NOT EXISTS student (
  student_id       CHAR(9)     NOT NULL,
  student_password VARCHAR(20) NOT NULL,
  student_name     VARCHAR(30) NOT NULL,
  clubs            INT DEFAULT 0,
  volunteer_hours  INT DEFAULT 0,
  PRIMARY KEY (student_id)
);

CREATE TABLE IF NOT EXISTS teacher (
  teacher_id       CHAR(6)     NOT NULL,
  teacher_password VARCHAR(20) NOT NULL,
  teacher_name     VARCHAR(30) NOT NULL,
  teacher_email    VARCHAR(50) DEFAULT 'No email',
  PRIMARY KEY (teacher_id)
);

CREATE TABLE IF NOT EXISTS course (
  course_id            VARCHAR(10) NOT NULL,
  knowledge_weight     INT DEFAULT 0,
  thinking_weight      INT DEFAULT 0,
  application_weight   INT DEFAULT 0,
  communication_weight INT DEFAULT 0,
  PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS student_course_association (
  student_course_association_id INT AUTO_INCREMENT,
  student_id                    CHAR(9)     NOT NULL,
  course_id                     VARCHAR(10) NOT NULL,
  teacher_id                    CHAR(6)     NOT NULL,
  lates                         INT DEFAULT 0,
  absences                      INT DEFAULT 0,
  PRIMARY KEY (student_course_association_id),
  FOREIGN KEY (student_id) REFERENCES student (student_id),
  FOREIGN KEY (course_id) REFERENCES course (course_id),
  FOREIGN KEY (teacher_id) REFERENCES teacher (teacher_id)
);

CREATE TABLE IF NOT EXISTS teacher_course_association (
  teacher_course_assocation_id INT AUTO_INCREMENT,
  teacher_id                   CHAR(6)     NOT NULL,
  course_id                    VARCHAR(10) NOT NULL,
  PRIMARY KEY (teacher_course_assocation_id),
  FOREIGN KEY (teacher_id) REFERENCES teacher (teacher_id),
  FOREIGN KEY (course_id) REFERENCES course (course_id)
);

CREATE TABLE IF NOT EXISTS assessment (
  course_id         VARCHAR(10) NOT NULL,
  assessment_id     VARCHAR(50) NOT NULL,
  weight            INT         NOT NULL,
  has_knowledge     BOOLEAN DEFAULT FALSE,
  has_application   BOOLEAN DEFAULT FALSE,
  has_thinking      BOOLEAN DEFAULT FALSE,
  has_communication BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (assessment_id),
  FOREIGN KEY (course_id) REFERENCES course (course_id)
);

CREATE TABLE IF NOT EXISTS mark (
  student_course_association_id INT         NOT NULL,
  assessment_id                 VARCHAR(50) NOT NULL,
  knowledge_mark                INT DEFAULT NULL,
  thinking_mark                 INT DEFAULT NULL,
  application_mark              INT DEFAULT NULL,
  communication_mark            INT DEFAULT NULL,
  FOREIGN KEY (student_course_association_id) REFERENCES student_course_association (student_course_association_id),
  FOREIGN KEY (assessment_id) REFERENCES assessment (assessment_id)
);

ALTER TABLE mark
  ADD CONSTRAINT unique_mark UNIQUE (student_course_association_id, assessment_id);

