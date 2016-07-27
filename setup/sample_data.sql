INSERT INTO student (student_id, student_password, student_name) VALUES
  ('123456789', 'andi', 'Andi'),
  ('123456788', 'bob', 'Bob'),
  ('123456787', 'cabot', 'Cabot');

INSERT INTO teacher VALUES
  ('123456', 'sir', 'Mr. Sir', 'sir@gmail.com'),
  ('123455', 'madam', 'Mme. Madam', 'madam@gmail.com'),
  ('123454', 'missus', 'Ms. Missus', 'missus@gmail.com');

INSERT INTO course VALUES
  ('math', 1, 1, 1, 1),
  ('physics', 2, 2, 2, 2),
  ('chemistry', 3, 3, 3, 3),
  ('biology', 4, 4, 4, 4);

INSERT INTO student_course_association (student_id, course_id, teacher_id) VALUES
  ('123456789', 'math', '123456'),
  ('123456789', 'physics', '123455'),
  ('123456788', 'biology', '123455'),
  ('123456788', 'chemistry', '123454'),
  ('123456787', 'biology', '123455'),
  ('123456787', 'math', '123456');

INSERT INTO teacher_course_association (teacher_id, course_id) VALUES
  ('123456', 'math'),
  ('123455', 'physics'),
  ('123455', 'biology'),
  ('123454', 'chemistry'),
  ('123456', 'chemistry');

INSERT INTO assessment VALUES
  ('math', 'math_test', 1, true, true, false, false),
  ('math', 'math_quiz', 300, true, true, false, false),
  ('physics', 'physics_test', 2, true, true, false, false),
  ('chemistry', 'chem_test', 2, true, true, false, false),
  ('biology', 'bio_test', 2, true, true, false, false);

INSERT INTO mark (student_course_association_id, assessment_id, knowledge_mark)
  (SELECT
     student_course_association_id,
     'math_test',
     80
   FROM student_course_association
   WHERE course_id = 'math' AND teacher_id = '123456' AND student_id = '123456789');