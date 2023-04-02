SELECT Students.id, GROUP_CONCAT(Departments.name SEPARATOR ', ') AS minors
FROM Students
LEFT JOIN Minors ON Students.id = Minors .sid
LEFT JOIN Departments ON Minors.dname = Departments.name
GROUP BY Students.id;