-- =============================================================
--  Student Absence Management System – seed data
-- =============================================================

-- Instructors
INSERT INTO instructor (first_name, last_name, email) VALUES
('Nikos',    'Papadopoulos', 'n.papadopoulos@techpro.gr'),
('Maria',    'Georgiou',     'm.georgiou@techpro.gr'),
('Kostas',   'Alexiou',      'k.alexiou@techpro.gr');

-- Modules
INSERT INTO module (code, title, credits, semester, academic_year) VALUES
('CS101', 'Introduction to Programming',     6, 'FALL',   2025),
('CS201', 'Data Structures & Algorithms',    6, 'FALL',   2025),
('CS301', 'Database Systems',                5, 'SPRING', 2026),
('CS401', 'Software Engineering',            5, 'SPRING', 2026),
('CS501', 'Networks & Security',             4, 'FALL',   2025);

-- Module–instructor assignments
INSERT INTO module_instructor (module_id, instructor_id, role) VALUES
(1, 1, 'LEAD'),
(2, 1, 'LEAD'),
(2, 2, 'ASSISTANT'),
(3, 2, 'LEAD'),
(4, 3, 'LEAD'),
(5, 3, 'LEAD'),
(5, 1, 'ASSISTANT');

-- Students
INSERT INTO student (first_name, last_name, email, student_number, enrollment_date) VALUES
('Alexandros', 'Nikolaou',    'a.nikolaou@students.techpro.gr',  'STU2025001', '2025-09-01'),
('Eleni',      'Papadaki',    'e.papadaki@students.techpro.gr',  'STU2025002', '2025-09-01'),
('Dimitris',   'Stavros',     'd.stavros@students.techpro.gr',   'STU2025003', '2025-09-01'),
('Sofia',      'Katsarou',    's.katsarou@students.techpro.gr',  'STU2025004', '2025-09-01'),
('Giorgos',    'Makris',      'g.makris@students.techpro.gr',    'STU2025005', '2025-09-01'),
('Anna',       'Theodorou',   'a.theodorou@students.techpro.gr', 'STU2025006', '2025-09-01'),
('Petros',     'Lambros',     'p.lambros@students.techpro.gr',   'STU2025007', '2025-09-01'),
('Christina',  'Vasiliou',    'c.vasiliou@students.techpro.gr',  'STU2025008', '2025-09-01'),
('Nikos',      'Zafeiris',    'n.zafeiris@students.techpro.gr',  'STU2025009', '2025-09-01'),
('Katerina',   'Mpousdoukos', 'k.mpousdoukos@students.techpro.gr','STU2025010','2025-09-01');

-- Enrollments (every student in CS101; mixed for others)
INSERT INTO enrollment (student_id, module_id, enrolled_at, status) VALUES
-- CS101
(1,1,'2025-09-01','ACTIVE'),(2,1,'2025-09-01','ACTIVE'),(3,1,'2025-09-01','ACTIVE'),
(4,1,'2025-09-01','ACTIVE'),(5,1,'2025-09-01','ACTIVE'),(6,1,'2025-09-01','ACTIVE'),
(7,1,'2025-09-01','ACTIVE'),(8,1,'2025-09-01','ACTIVE'),(9,1,'2025-09-01','ACTIVE'),
(10,1,'2025-09-01','ACTIVE'),
-- CS201
(1,2,'2025-09-01','ACTIVE'),(2,2,'2025-09-01','ACTIVE'),(3,2,'2025-09-01','ACTIVE'),
(4,2,'2025-09-01','ACTIVE'),(5,2,'2025-09-01','ACTIVE'),
-- CS301
(6,3,'2026-02-01','ACTIVE'),(7,3,'2026-02-01','ACTIVE'),(8,3,'2026-02-01','ACTIVE'),
(9,3,'2026-02-01','ACTIVE'),(10,3,'2026-02-01','ACTIVE'),
-- CS401
(1,4,'2026-02-01','ACTIVE'),(3,4,'2026-02-01','ACTIVE'),(5,4,'2026-02-01','ACTIVE'),
-- CS501
(2,5,'2025-09-01','ACTIVE'),(4,5,'2025-09-01','ACTIVE'),(6,5,'2025-09-01','ACTIVE');

-- Sessions for CS101 (module_id=1) — 8 lectures
INSERT INTO session (module_id, session_date, start_time, end_time, session_type, topic) VALUES
(1,'2025-10-01','09:00','11:00','LECTURE','Introduction & Setup'),
(1,'2025-10-08','09:00','11:00','LECTURE','Variables and Data Types'),
(1,'2025-10-15','09:00','11:00','LAB',    'Lab: Hello World & Basic I/O'),
(1,'2025-10-22','09:00','11:00','LECTURE','Control Flow'),
(1,'2025-10-29','09:00','11:00','LECTURE','Functions & Scope'),
(1,'2025-11-05','09:00','11:00','LAB',    'Lab: Functions'),
(1,'2025-11-12','09:00','11:00','LECTURE','Arrays & Collections'),
(1,'2025-11-19','09:00','11:00','SEMINAR','Seminar: Problem Solving');

-- Sessions for CS201 (module_id=2) — 6 sessions
INSERT INTO session (module_id, session_date, start_time, end_time, session_type, topic) VALUES
(2,'2025-10-02','11:00','13:00','LECTURE','Big-O Notation'),
(2,'2025-10-09','11:00','13:00','LECTURE','Linked Lists'),
(2,'2025-10-16','11:00','13:00','LAB',    'Lab: Linked Lists'),
(2,'2025-10-23','11:00','13:00','LECTURE','Stacks & Queues'),
(2,'2025-10-30','11:00','13:00','LECTURE','Trees & Graphs'),
(2,'2025-11-06','11:00','13:00','LAB',    'Lab: Trees');

-- Sessions for CS301 (module_id=3) — 6 sessions
INSERT INTO session (module_id, session_date, start_time, end_time, session_type, topic) VALUES
(3,'2026-02-11','14:00','16:00','LECTURE','Relational Model'),
(3,'2026-02-18','14:00','16:00','LECTURE','SQL Fundamentals'),
(3,'2026-02-25','14:00','16:00','LAB',    'Lab: DDL & DML'),
(3,'2026-03-04','14:00','16:00','LECTURE','Normalization'),
(3,'2026-03-11','14:00','16:00','LECTURE','Indexes & Query Optimization'),
(3,'2026-03-18','14:00','16:00','SEMINAR','Seminar: Schema Design');

-- Absences for CS101 (enrollment_ids 1-10 map to students 1-10 in module 1)
-- session_ids 1-8 belong to CS101
INSERT INTO absence (enrollment_id, session_id, status, justified, justification) VALUES
-- Student 1 (enrollment 1): mostly present
(1,1,'PRESENT',false,null),(1,2,'PRESENT',false,null),(1,3,'ABSENT',true,'Medical certificate'),
(1,4,'PRESENT',false,null),(1,5,'PRESENT',false,null),(1,6,'PRESENT',false,null),
(1,7,'ABSENT',false,null),(1,8,'PRESENT',false,null),
-- Student 2 (enrollment 2): several absences
(2,1,'ABSENT',false,null),(2,2,'PRESENT',false,null),(2,3,'ABSENT',false,null),
(2,4,'ABSENT',true,'Family emergency'),(2,5,'PRESENT',false,null),(2,6,'ABSENT',false,null),
(2,7,'PRESENT',false,null),(2,8,'ABSENT',false,null),
-- Student 3 (enrollment 3): late often
(3,1,'LATE',false,null),(3,2,'PRESENT',false,null),(3,3,'PRESENT',false,null),
(3,4,'LATE',false,null),(3,5,'ABSENT',false,null),(3,6,'PRESENT',false,null),
(3,7,'PRESENT',false,null),(3,8,'LATE',false,null),
-- Student 4 (enrollment 4): at risk (many absences)
(4,1,'ABSENT',false,null),(4,2,'ABSENT',false,null),(4,3,'ABSENT',false,null),
(4,4,'PRESENT',false,null),(4,5,'ABSENT',false,null),(4,6,'ABSENT',false,null),
(4,7,'ABSENT',false,null),(4,8,'PRESENT',false,null),
-- Student 5 (enrollment 5): perfect attendance
(5,1,'PRESENT',false,null),(5,2,'PRESENT',false,null),(5,3,'PRESENT',false,null),
(5,4,'PRESENT',false,null),(5,5,'PRESENT',false,null),(5,6,'PRESENT',false,null),
(5,7,'PRESENT',false,null),(5,8,'PRESENT',false,null),
-- Students 6–10 (enrollments 6–10): varied
(6,1,'PRESENT',false,null),(6,2,'ABSENT',false,null),(6,3,'PRESENT',false,null),
(6,4,'ABSENT',false,null),(6,5,'PRESENT',false,null),(6,6,'ABSENT',true,'Study trip'),
(6,7,'PRESENT',false,null),(6,8,'PRESENT',false,null),
(7,1,'ABSENT',false,null),(7,2,'PRESENT',false,null),(7,3,'LATE',false,null),
(7,4,'PRESENT',false,null),(7,5,'ABSENT',false,null),(7,6,'PRESENT',false,null),
(7,7,'ABSENT',false,null),(7,8,'PRESENT',false,null),
(8,1,'PRESENT',false,null),(8,2,'PRESENT',false,null),(8,3,'PRESENT',false,null),
(8,4,'ABSENT',false,null),(8,5,'PRESENT',false,null),(8,6,'PRESENT',false,null),
(8,7,'PRESENT',false,null),(8,8,'ABSENT',false,null),
(9,1,'ABSENT',false,null),(9,2,'ABSENT',false,null),(9,3,'ABSENT',false,null),
(9,4,'ABSENT',false,null),(9,5,'PRESENT',false,null),(9,6,'ABSENT',false,null),
(9,7,'PRESENT',false,null),(9,8,'ABSENT',false,null),
(10,1,'PRESENT',false,null),(10,2,'LATE',false,null),(10,3,'PRESENT',false,null),
(10,4,'PRESENT',false,null),(10,5,'LATE',false,null),(10,6,'PRESENT',false,null),
(10,7,'PRESENT',false,null),(10,8,'PRESENT',false,null);

-- Absences for CS201 (enrollment_ids 11-15, session_ids 9-14)
INSERT INTO absence (enrollment_id, session_id, status, justified, justification) VALUES
(11,9,'PRESENT',false,null),(11,10,'ABSENT',false,null),(11,11,'PRESENT',false,null),
(11,12,'PRESENT',false,null),(11,13,'ABSENT',true,'Medical'),(11,14,'PRESENT',false,null),
(12,9,'ABSENT',false,null),(12,10,'PRESENT',false,null),(12,11,'ABSENT',false,null),
(12,12,'PRESENT',false,null),(12,13,'PRESENT',false,null),(12,14,'ABSENT',false,null),
(13,9,'PRESENT',false,null),(13,10,'PRESENT',false,null),(13,11,'PRESENT',false,null),
(13,12,'ABSENT',false,null),(13,13,'PRESENT',false,null),(13,14,'PRESENT',false,null),
(14,9,'ABSENT',false,null),(14,10,'ABSENT',false,null),(14,11,'ABSENT',false,null),
(14,12,'ABSENT',false,null),(14,13,'PRESENT',false,null),(14,14,'ABSENT',false,null),
(15,9,'PRESENT',false,null),(15,10,'PRESENT',false,null),(15,11,'LATE',false,null),
(15,12,'PRESENT',false,null),(15,13,'PRESENT',false,null),(15,14,'PRESENT',false,null);
