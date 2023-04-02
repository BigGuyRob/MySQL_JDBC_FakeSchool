use cs336hw3;
drop table majors;
drop table minors;
CREATE TABLE IF NOT EXISTS Majors (
    sid CHAR(9) NOT NULL,
    dname VARCHAR(20) NOT NULL,
    FOREIGN KEY (sid) REFERENCES Students(id),
    FOREIGN KEY (dname) REFERENCES Departments(name),
    PRIMARY KEY (sid, dname)
);

CREATE TABLE IF NOT EXISTS Minors (
    sid CHAR(9) NOT NULL,
    dname VARCHAR(20) NOT NULL,
    FOREIGN KEY (sid) REFERENCES Students(id),
    FOREIGN KEY (dname) REFERENCES Departments(name),
    PRIMARY KEY (sid, dname)
);