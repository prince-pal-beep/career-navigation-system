-- AI-Powered Career Navigation System : MySQL schema + sample data
CREATE DATABASE IF NOT EXISTS career_nav CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE career_nav;

CREATE TABLE IF NOT EXISTS users (
  user_id       INT AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(100) NOT NULL,
  email         VARCHAR(100) NOT NULL UNIQUE,
  password      VARCHAR(255) NOT NULL,
  role          ENUM('CANDIDATE','EMPLOYER','ADMIN') NOT NULL DEFAULT 'CANDIDATE',
  qualification VARCHAR(100),
  experience    INT DEFAULT 0,
  interests     VARCHAR(255),
  status        ENUM('ACTIVE','BLOCKED') NOT NULL DEFAULT 'ACTIVE',
  created_on    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS employers (
  employer_id  INT AUTO_INCREMENT PRIMARY KEY,
  user_id      INT NOT NULL UNIQUE,
  company_name VARCHAR(150) NOT NULL,
  email        VARCHAR(100) NOT NULL,
  contact_no   VARCHAR(20),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS skills (
  skill_id   INT AUTO_INCREMENT PRIMARY KEY,
  skill_name VARCHAR(100) NOT NULL UNIQUE,
  skill_type VARCHAR(50)  NOT NULL DEFAULT 'Technical'
);

CREATE TABLE IF NOT EXISTS user_skills (
  user_id           INT NOT NULL,
  skill_id          INT NOT NULL,
  proficiency_level INT NOT NULL DEFAULT 1,           -- 1 (beginner) .. 5 (expert)
  acquired_on       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, skill_id),
  FOREIGN KEY (user_id)  REFERENCES users(user_id)   ON DELETE CASCADE,
  FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS careers (
  career_id   INT AUTO_INCREMENT PRIMARY KEY,
  career_name VARCHAR(150) NOT NULL,
  description TEXT,
  category    VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS career_skills (
  career_id        INT NOT NULL,
  skill_id         INT NOT NULL,
  importance_level INT NOT NULL DEFAULT 3,            -- required level 1..5
  PRIMARY KEY (career_id, skill_id),
  FOREIGN KEY (career_id) REFERENCES careers(career_id) ON DELETE CASCADE,
  FOREIGN KEY (skill_id)  REFERENCES skills(skill_id)   ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS learning_resources (
  resource_id   INT AUTO_INCREMENT PRIMARY KEY,
  title         VARCHAR(200) NOT NULL,
  resource_type VARCHAR(50)  NOT NULL,                -- Course / Documentation / Certification ...
  link          VARCHAR(500),
  career_id     INT NULL,
  skill_id      INT NULL,
  FOREIGN KEY (career_id) REFERENCES careers(career_id) ON DELETE CASCADE,
  FOREIGN KEY (skill_id)  REFERENCES skills(skill_id)   ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS jobs (
  job_id              INT AUTO_INCREMENT PRIMARY KEY,
  employer_id         INT NOT NULL,
  job_title           VARCHAR(150) NOT NULL,
  description         TEXT,
  location            VARCHAR(100),
  required_experience INT DEFAULT 0,
  salary_range        VARCHAR(50),
  status              ENUM('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING',
  posted_on           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (employer_id) REFERENCES employers(employer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS job_skills (
  job_id   INT NOT NULL,
  skill_id INT NOT NULL,
  PRIMARY KEY (job_id, skill_id),
  FOREIGN KEY (job_id)   REFERENCES jobs(job_id)     ON DELETE CASCADE,
  FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS applications (
  application_id   INT AUTO_INCREMENT PRIMARY KEY,
  user_id          INT NOT NULL,
  job_id           INT NOT NULL,
  application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status           ENUM('APPLIED','SHORTLISTED','SELECTED','REJECTED') NOT NULL DEFAULT 'APPLIED',
  UNIQUE KEY uq_user_job (user_id, job_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (job_id)  REFERENCES jobs(job_id)   ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS feedback (
  feedback_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id     INT,
  comments    TEXT NOT NULL,
  created_on  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS contact_messages (
  contact_id INT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  email      VARCHAR(100),
  message    TEXT NOT NULL,
  created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------- Sample data (the ADMIN account is created automatically on first start) ----------
INSERT IGNORE INTO skills (skill_id, skill_name, skill_type) VALUES
 (1,'Java','Technical'),(2,'Python','Technical'),(3,'SQL','Technical'),(4,'HTML & CSS','Technical'),
 (5,'JavaScript','Technical'),(6,'Machine Learning','Technical'),(7,'Statistics','Technical'),(8,'Git','Technical'),
 (9,'Communication','Soft'),(10,'Problem Solving','Soft'),(11,'Data Visualization','Technical'),
 (12,'Networking','Technical'),(13,'Linux','Technical'),(14,'Network Security','Technical');

INSERT IGNORE INTO careers (career_id, career_name, description, category) VALUES
 (1,'Software Developer','Designs, builds and maintains software applications.','Software'),
 (2,'Data Scientist','Builds models and extracts insight from large datasets.','Data & AI'),
 (3,'Web Developer','Creates responsive websites and web applications.','Software'),
 (4,'Cybersecurity Analyst','Protects systems and networks from attacks.','Security'),
 (5,'Data Analyst','Collects, cleans and interprets data to support decisions.','Data & AI');

INSERT IGNORE INTO career_skills (career_id, skill_id, importance_level) VALUES
 (1,1,5),(1,3,4),(1,8,3),(1,10,4),
 (2,2,5),(2,6,5),(2,7,5),(2,3,4),(2,11,3),
 (3,4,5),(3,5,5),(3,8,3),(3,10,3),(3,3,2),
 (4,12,5),(4,13,4),(4,14,5),(4,10,3),
 (5,3,5),(5,7,4),(5,11,4),(5,2,3),(5,9,3);

INSERT IGNORE INTO learning_resources (resource_id, title, resource_type, link, career_id, skill_id) VALUES
 (1,'Dev.java - Learn Java','Documentation','https://dev.java/learn/',NULL,1),
 (2,'The Python Tutorial','Documentation','https://docs.python.org/3/tutorial/',NULL,2),
 (3,'SQL Tutorial','Course','https://www.w3schools.com/sql/',NULL,3),
 (4,'MDN Learn Web Development','Course','https://developer.mozilla.org/en-US/docs/Learn',NULL,4),
 (5,'The Modern JavaScript Tutorial','Course','https://javascript.info/',NULL,5),
 (6,'Machine Learning Specialization','Certification','https://www.coursera.org/specializations/machine-learning-introduction',NULL,6),
 (7,'Statistics and Probability','Course','https://www.khanacademy.org/math/statistics-probability',NULL,7),
 (8,'Pro Git Book','Documentation','https://git-scm.com/book/en/v2',NULL,8),
 (9,'Communication Skills Practice','Practice Task',NULL,NULL,9),
 (10,'Solve 50 beginner problems on a coding practice site','Practice Task',NULL,NULL,10),
 (11,'Build 3 dashboards from open datasets','Practice Task',NULL,NULL,11),
 (12,'Computer Networking Fundamentals','Course','https://www.cloudflare.com/learning/',NULL,12),
 (13,'Linux Command Line Basics','Course','https://linuxjourney.com/',NULL,13),
 (14,'Network Security Fundamentals','Certification','https://www.cisco.com/c/en/us/training-events/training-certifications.html',NULL,14);
