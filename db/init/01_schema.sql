-- =============================================================
--  Student Absence Management System – schema
--  Executed automatically by PostgreSQL on first container start
-- =============================================================

-- instructors
CREATE TABLE IF NOT EXISTS instructor (
    id          BIGSERIAL    PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- modules
CREATE TABLE IF NOT EXISTS module (
    id             BIGSERIAL    PRIMARY KEY,
    code           VARCHAR(20)  NOT NULL UNIQUE,
    title          VARCHAR(255) NOT NULL,
    credits        INT          NOT NULL CHECK (credits > 0),
    semester       VARCHAR(20)  NOT NULL,   -- FALL | SPRING | SUMMER
    academic_year  INT          NOT NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- module–instructor assignments
CREATE TABLE IF NOT EXISTS module_instructor (
    id             BIGSERIAL PRIMARY KEY,
    module_id      BIGINT NOT NULL REFERENCES module(id)     ON DELETE CASCADE,
    instructor_id  BIGINT NOT NULL REFERENCES instructor(id) ON DELETE CASCADE,
    role           VARCHAR(50) NOT NULL DEFAULT 'LEAD',      -- LEAD | ASSISTANT
    UNIQUE (module_id, instructor_id)
);

-- students
CREATE TABLE IF NOT EXISTS student (
    id               BIGSERIAL    PRIMARY KEY,
    first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    email            VARCHAR(255) NOT NULL UNIQUE,
    student_number   VARCHAR(50)  NOT NULL UNIQUE,
    enrollment_date  DATE         NOT NULL DEFAULT CURRENT_DATE,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- enrollments (student <-> module)
CREATE TABLE IF NOT EXISTS enrollment (
    id           BIGSERIAL   PRIMARY KEY,
    student_id   BIGINT      NOT NULL REFERENCES student(id) ON DELETE CASCADE,
    module_id    BIGINT      NOT NULL REFERENCES module(id)  ON DELETE CASCADE,
    enrolled_at  DATE        NOT NULL DEFAULT CURRENT_DATE,
    status       VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',      -- ACTIVE | DROPPED | COMPLETED
    UNIQUE (student_id, module_id)
);

-- sessions (individual class meetings)
CREATE TABLE IF NOT EXISTS session (
    id            BIGSERIAL    PRIMARY KEY,
    module_id     BIGINT       NOT NULL REFERENCES module(id) ON DELETE CASCADE,
    session_date  DATE         NOT NULL,
    start_time    TIME         NOT NULL,
    end_time      TIME         NOT NULL,
    session_type  VARCHAR(20)  NOT NULL DEFAULT 'LECTURE',   -- LECTURE | LAB | SEMINAR
    topic         VARCHAR(255),
    CHECK (end_time > start_time)
);

-- absences
CREATE TABLE IF NOT EXISTS absence (
    id             BIGSERIAL   PRIMARY KEY,
    enrollment_id  BIGINT      NOT NULL REFERENCES enrollment(id) ON DELETE CASCADE,
    session_id     BIGINT      NOT NULL REFERENCES session(id)    ON DELETE CASCADE,
    status         VARCHAR(20) NOT NULL DEFAULT 'ABSENT',    -- ABSENT | PRESENT | LATE
    justification  TEXT,
    justified      BOOLEAN     NOT NULL DEFAULT FALSE,
    recorded_at    TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (enrollment_id, session_id)
);

-- indexes for common query patterns
CREATE INDEX IF NOT EXISTS idx_absence_enrollment  ON absence(enrollment_id);
CREATE INDEX IF NOT EXISTS idx_absence_session     ON absence(session_id);
CREATE INDEX IF NOT EXISTS idx_session_module_date ON session(module_id, session_date);
CREATE INDEX IF NOT EXISTS idx_enrollment_student  ON enrollment(student_id);
CREATE INDEX IF NOT EXISTS idx_enrollment_module   ON enrollment(module_id);
