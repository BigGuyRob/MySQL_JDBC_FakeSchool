SELECT Students.id, GROUP_CONCAT(Departments.name SEPARATOR ', ') AS majors
FROM Students
LEFT JOIN Majors ON Students.id = Majors.sid
LEFT JOIN Departments ON Majors.dname = Departments.name
GROUP BY Students.id;