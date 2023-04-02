use cs336hw3;
drop table if exists Majors;
drop table if exists Minors;
drop table if exists IsTaking;
drop table if exists HasTaken;
drop table if exists Departments;
drop table if exists Students;
drop table if exists Classes;

CREATE TABLE Departments (
    name VARCHAR(20) NOT NULL,
    campus VARCHAR(20) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE Students (
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    id CHAR(9) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Classes (
    name VARCHAR(50) NOT NULL,
    credits INT NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE Majors (
    sid CHAR(9) NOT NULL,
    dname VARCHAR(20) NOT NULL,
    FOREIGN KEY (sid) REFERENCES Students(id),
    FOREIGN KEY (dname) REFERENCES Departments(name),
    PRIMARY KEY (sid, dname)
);

CREATE TABLE Minors (
    sid CHAR(9) NOT NULL,
    dname VARCHAR(20) NOT NULL,
    FOREIGN KEY (sid) REFERENCES Students(id),
    FOREIGN KEY (dname) REFERENCES Departments(name),
    PRIMARY KEY (sid, dname)
);

CREATE TABLE IsTaking (
    sid CHAR(9) NOT NULL,
    name VARCHAR(50) NOT NULL,
    FOREIGN KEY (sid) REFERENCES Students(id),
    FOREIGN KEY (name) REFERENCES Classes(name),
    PRIMARY KEY (sid, name)
);

CREATE TABLE HasTaken (
    sid CHAR(9) NOT NULL,
    name VARCHAR(50) NOT NULL,
    grade CHAR(1) NOT NULL,
    FOREIGN KEY (sid) REFERENCES Students(id),
    FOREIGN KEY (name) REFERENCES Classes(name),
    PRIMARY KEY (sid, name)
);