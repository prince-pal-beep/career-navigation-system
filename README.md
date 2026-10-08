# AI-Powered Career Navigation System
## About the project

Students, fresh graduates and career switchers often find it hard to choose a career, know which skills they are missing, and keep up with what employers want.

The **AI-Powered Career Navigation System** is a web application that guides users through the whole journey:

1. Build a **profile** with skills, proficiency levels, qualification and interests.
2. Get **career recommendations**, ranked by how well the profile fits each career.
3. See a **skill gap analysis** against a target career.
4. Follow a **learning roadmap** of courses and resources for each missing skill.
5. **Search and apply for jobs** and track the application status.

Employers post jobs and manage applicants, and administrators maintain the career and skill database, approve job posts and view reports.

### How the "AI" works

The project uses a **rule-based recommendation engine**:

- `CareerRecommendationEngine` scores each career out of 100:
  **80%** skill coverage (candidate level vs. the level the career requires) + **20%** interest-keyword match.
- `SkillGapService` compares the candidate's levels with a career's required levels, calculates a readiness %, and builds the learning roadmap from the missing skills (largest gap first).
- Job matching shows a match % based on how many of a job's required skills the candidate has.

The engine is kept in its own service layer, so it can later be replaced with a machine-learning model without changing the rest of the application.

---

## Features by role

| Role | Features |
|---|---|
| **Visitor** | Home, About, Contact, Sign up, Login |
| **Candidate** | Profile and skills, career recommendations, skill gap analysis, learning roadmap, job search with match %, apply, application tracking, change password |
| **Employer** | Dashboard, post jobs (with required skills), view applicants, shortlist / select / reject, change password |
| **Admin** | Dashboard with counts, approve / reject / delete jobs, block / delete users, manage careers and skills, reports, change password |

---

## Technology stack

| Layer | Technology |
|---|---|
| Front end | HTML, CSS, JavaScript, Bootstrap 5 |
| Back end | Java 11+, JSP, Servlets, JSTL |
| Database | MySQL 8 (JDBC) |
| Server | Apache Tomcat 9 |
| Build tool | Maven |
| Version control | Git and GitHub |

Architecture: **MVC** - Servlets (controller), JSP (view), DAO + model classes (data), and a service layer (recommendation logic).

---

## Project structure

```
career-navigation-system/
├── pom.xml                         Maven build file (WAR packaging)
├── README.md
└── src/main/
    ├── java/com/careernav/
    │   ├── config/                 DBConnection (singleton), AppConfig (start-up + password hashing)
    │   ├── controller/             Servlets (request handlers)
    │   │   ├── BaseServlet.java    shared helpers
    │   │   ├── ContactServlet.java
    │   │   ├── auth/               Login, Register, Logout, ChangePassword
    │   │   ├── candidate/          Profile, CareerRecommendation, SkillGap, LearningRoadmap, JobApply
    │   │   ├── employer/           EmployerDashboard, PostJob, ManageApplicants
    │   │   └── admin/              AdminDashboard, ManageCareers, ManageSkills
    │   ├── dao/                    Data access: User, Career, Skill, Job, Application, Message
    │   ├── model/                  Java beans: User, Employer, Career, Skill, Job, Application, LearningResource
    │   ├── service/                CareerRecommendationEngine, SkillGapService
    │   └── filter/                 AuthenticationFilter, RoleAccessFilter
    ├── resources/
    │   ├── db.properties           database settings (not committed - see setup)
    │   ├── db.properties.example   template for db.properties
    │   └── schema.sql              tables + sample careers, skills and learning resources
    └── webapp/
        ├── index.jsp, about.jsp, contact.jsp
        ├── assets/                 css, js, images
        └── WEB-INF/
            ├── web.xml
            └── views/              JSP pages (not reachable by direct URL)
                ├── common/         header, footer, navbar
                ├── auth/           login, register, change-password
                ├── candidate/      profile, recommendations, skill-gap, roadmap, job-search
                ├── employer/       dashboard, post-job, applicants
                └── admin/          dashboard, manage-careers, manage-skills, reports
```

### Database tables

`users`, `employers`, `skills`, `user_skills`, `careers`, `career_skills`, `learning_resources`, `jobs`, `job_skills`, `applications`, `feedback`, `contact_messages`

---

## How to run it

### Requirements
- JDK 17 (or 11+)
- Maven 3.9+
- MySQL 8
- Apache Tomcat **9** (Tomcat 10+ will not work - it uses `jakarta.*` instead of `javax.*`)

### Steps
1. **Clone** the repository
   ```
   git clone https://github.com/prince-pal-beep/career-navigation-system.git
   cd career-navigation-system
   ```
2. **Create the database** - run `src/main/resources/schema.sql` in MySQL (Workbench or the `mysql` command line).
3. **Configure the connection** - copy the template and put in your MySQL password:
   ```
   copy src\main\resources\db.properties.example src\main\resources\db.properties
   ```
   Then edit `db.properties` and set `db.password` (no quotes).
4. **Build**
   ```
   mvn clean package
   ```
5. **Deploy** - copy `target/career-navigation-system.war` into Tomcat's `webapps` folder and start Tomcat (`bin\startup.bat`).
6. **Open** `http://localhost:8080/career-navigation-system/`

### Default admin account
Created automatically the first time the application starts (and can reach the database):

| Email | Password |
|---|---|
| `admin@careernav.com` | `Admin@123` |

Change this password after your first login.

---

## Main URLs

| Area | URLs |
|---|---|
| Public | `/index.jsp`, `/about.jsp`, `/contact.jsp`, `/login`, `/register` |
| Candidate | `/candidate/profile`, `/candidate/recommendations`, `/candidate/skill-gap`, `/candidate/roadmap`, `/candidate/jobs` |
| Employer | `/employer/dashboard`, `/employer/post-job`, `/employer/applicants?jobId=` |
| Admin | `/admin/dashboard` (add `?view=reports` for reports), `/admin/careers`, `/admin/skills` |

---

## Security

- Passwords stored as salted **PBKDF2** hashes
- All SQL uses `PreparedStatement` (protects against SQL injection)
- Session-fixation protection on login, session cookie is HTTP-only
- URL access controlled by role-based filters
- Output escaped with `<c:out>` (protects against XSS)
- JSP pages sit under `WEB-INF` so they cannot be opened directly

---

## Future scope

- Machine-learning based recommendations
- Skill assessment tests and quizzes
- Resume builder
- Job alert notifications
- Chat between candidates and employers
- Live career counselling sessions
- AI mock interviews
- Mobile app

---

## Academic note

This project was developed by Prince Pal