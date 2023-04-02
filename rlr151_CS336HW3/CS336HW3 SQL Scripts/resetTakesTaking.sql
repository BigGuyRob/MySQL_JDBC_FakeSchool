use cs336hw3;
drop table if exists IsTaking;
drop table if exists HasTaken;
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