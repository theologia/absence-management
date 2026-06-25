# Student Absence Management System

A REST API for tracking student absences originally created for TechPro Academy. 
Built with Spring Boot 3, PostgreSQL, and Docker Compose.

## Table of Contents

- [Prerequisites](#prerequisites)
- [How to Start the Stack](#how-to-start-the-stack)
- [How to Stop and Clean Up](#how-to-stop-and-clean-up)
- [Environment Variables](#environment-variables)
- [API Overview](#api-overview)
- [curl Examples](#curl-examples)

## Prerequisites

| Tool | Minimum version |
|------|----------------|
| Docker Desktop | 24.x |
| Docker Compose | v2 (bundled with Docker Desktop) |
| curl | any recent version (for the examples below) |

Note!: No local JDK or Maven installation is needed — the Dockerfile handles the build inside a container.

## How to Start

Open a terminal and then:

1. **Clone the repository**
   git clone <repo-url>
   cd absence-management

2. **Review (and optionally edit) the environment file**

   The `.env` file at the project root controls all runtime configuration. Defaults work out of the box
   see [Environment Variables](#environment-variables) for details.

3. **Build and start all services**
   docker compose up --build -d

   This starts the three containers:
   - **db** — PostgreSQL 15, initialised automatically from `db/init/`
   - **app** — The Spring Boot API, available at `http://localhost:8080`
   - **pgadmin** — pgAdmin 4 UI, available at `http://localhost:5050`

4. **Verify the API is up**

   ```bash
   curl -s http://localhost:8080/api/students | head -c 200
   ```

   You should receive a JSON array (possibly empty if no student exists).

5. **Browse the interactive API docs**

   Open `http://localhost:8080/swagger-ui.html` in a browser.

---

## How to Stop and Clean Up

**Stop the containers (keep data)**

```bash
docker compose down
```

**Stop and delete all data (volumes)**

```bash
docker compose down -v
```

**Remove built images as well**

```bash
docker compose down -v --rmi local
```

---

## Environment Variables

All variables live in the `.env` file. Docker Compose reads this file automatically.

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_NAME` | `absence_db` | Name of the PostgreSQL database created on first start |
| `DB_USER` | `absence_user` | PostgreSQL username the application connects with |
| `DB_PASSWORD` | `secret123` | PostgreSQL password — change before deploying to any shared environment |
| `DB_PORT` | `5432` | Host port mapped to the PostgreSQL container |
| `APP_PORT` | `8080` | Host port mapped to the Spring Boot application |
| `ABSENCE_THRESHOLD` | `33` | Percentage of absences above which a student is considered "at risk" in report endpoints |
| `PGADMIN_EMAIL` | `admin@techpro.gr` | Login e-mail for the pgAdmin web interface |
| `PGADMIN_PASSWORD` | `admin123` | Login password for pgAdmin |
| `PGADMIN_PORT` | `5050` | Host port mapped to the pgAdmin container |

---

## API Overview

| Resource | Base path | Operations |
|----------|-----------|------------|
| Students | `/api/students` | Create, read, update, delete |
| Instructors | `/api/instructors` | Create, read, update, delete |
| Modules | `/api/modules` | Create, read, update, delete; assign/remove instructors |
| Sessions | `/api/modules/{moduleId}/sessions` | Add session, list with optional date-range filter |
| Enrollments | `/api/enrollments` | Enroll student, get enrollment, drop enrollment |
| Absences | `/api/absences` | Record absence/attendance, justify/unjustify, filter query |
| Reports | `/api/reports` | Absence summary, at-risk students, module statistics |

Full parameter documentation is available at `http://localhost:8080/swagger-ui.html`.

---

## curl Examples

> All examples assume the API is running on `http://localhost:8080`.
> Replace placeholder IDs (e.g. `1`, `2`) with real IDs returned by the environment.

---

### 1. Create a Student

Add a new student in the system.

```bash
curl -s -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Theologia",
    "lastName": "Gougoula",
    "email": "theogoug@dummymail.gr",
    "studentNumber": "S2024001",
    "enrollmentDate": "2026-04-01"
  }' | jq .
```

**What it does:** Persists a `Student` row and returns the full student object with the generated `id`. The `email` and `studentNumber` fields are unique - a 409 error is returned if either already exists.

---

### 2. List All Students

Retrieves every student currently in the database.

```bash
curl -s http://localhost:8080/api/students | jq .
```

**What it does:** Returns an array of `StudentResponse` objects. Use the `id` values from this response in subsequent enrollment and absence calls.

---

### 3. Record an Absence

Marks a student (via their enrollment) as `ABSENT`, `PRESENT`, or `LATE` for a specific session.

```bash
curl -s -X POST http://localhost:8080/api/absences \
  -H "Content-Type: application/json" \
  -d '{
    "enrollmentId": 1,
    "sessionId": 1,
    "status": "ABSENT"
  }' | jq .
```

**What it does:** Creates an `Absence` record linking an enrollment to a session. The combination of `enrollmentId` + `sessionId` is unique, so the same session cannot be recorded twice for the same student. Returns the `AbsenceResponse` including the new absence `id`.

---

### 4. Justify an Absence

Updates an existing absence record to mark it as justified (e.g. medical certificate provided).

```bash
curl -s -X PATCH http://localhost:8080/api/absences/1/justify \
  -H "Content-Type: application/json" \
  -d '{
    "justified": true,
    "justification": "Medical certificate submitted on 2024-10-05"
  }' | jq .
```

**What it does:** Sets `justified = true` and stores the free-text `justification` string on the absence row identified by `1`. Justified absences are excluded from the at-risk threshold calculation. Pass `"justified": false` to revoke a justification.

---

### 5. Get an Absence Summary for a Student in a Module

Returns a breakdown of attended vs. absent sessions and the calculated absence rate.

```bash
curl -s "http://localhost:8080/api/reports/summary?studentId=1&moduleId=1" | jq .
```

**What it does:** Queries all sessions for the module, counts the student's attendance records, and returns an `AbsenceSummaryResponse` containing `totalSessions`, `attended`, `absent`, `justifiedAbsences`, and `absenceRate` (as a percentage). Useful for end-of-semester progress checks.

---

### 6. Get At-Risk Students

Lists every student whose absence rate exceeds the configured threshold (`ABSENCE_THRESHOLD` in `.env`).

```bash
curl -s "http://localhost:8080/api/reports/at-risk?moduleId=1" | jq .
```

**What it does:** For a given module, calculates each enrolled student's absence rate and returns only those at or above the threshold (default 33%). The response includes `studentId`, `studentName`, and the computed `absenceRate`, enabling instructors to intervene before the end of the semester.

---

### 7. Delete a Student

Permanently removes a student and their related records.

```bash
curl -s -X DELETE http://localhost:8080/api/students/1 \
  -o /dev/null -w "%{http_code}\n"
```

**What it does:** Issues a `DELETE` against the student row with `id = 1`. Returns `204 No Content` on success, `404` if the student does not exist.

---

### Tip: pretty-print without `jq`

If `jq` is not installed, append `| python -m json.tool` to any command above.
