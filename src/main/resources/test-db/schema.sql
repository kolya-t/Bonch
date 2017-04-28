CREATE TABLE "faculty" (
  id   INT         NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY ( START WITH 1, INCREMENT BY 1),
  name VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE "group" (
  id           VARCHAR(45) NOT NULL PRIMARY KEY,
  course       INT         NOT NULL,
  specialty_id VARCHAR(8)  NOT NULL
);

CREATE TABLE "specialty" (
  id         VARCHAR(8)   NOT NULL PRIMARY KEY,
  name       VARCHAR(100) NOT NULL UNIQUE,
  faculty_id INT          NOT NULL
);

CREATE TABLE "student" (
  id         INT         NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY ( START WITH 1, INCREMENT BY 1),
  first_name VARCHAR(45) NOT NULL,
  last_name  VARCHAR(45) NOT NULL,
  birth_date DATE                             DEFAULT NULL,
  group_id   VARCHAR(45) NOT NULL
);