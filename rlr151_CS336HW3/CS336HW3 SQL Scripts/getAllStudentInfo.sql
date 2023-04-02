SELECT gpa_info.id, gpa_info.first_name, gpa_info.last_name, gpa_info.majors, gpa_info.minors, gpa_info.gpa, 
       credit_info.total_credits
FROM 
    (SELECT Students.id, Students.first_name, Students.last_name,
       GROUP_CONCAT(DISTINCT DepartmentsMajors.name SEPARATOR ', ') AS majors,
       GROUP_CONCAT(DISTINCT DepartmentsMinors.name SEPARATOR ', ') AS minors,
       (SUM(CASE HasTaken.grade
            WHEN 'A' THEN 4
            WHEN 'B' THEN 3
            WHEN 'C' THEN 2
            WHEN 'D' THEN 1
            ELSE 0
        END * Classes.credits) /
       SUM(Classes.credits)) AS gpa
    FROM Students 
    LEFT JOIN HasTaken ON Students.id = HasTaken.sid
    LEFT JOIN Classes ON HasTaken.name = Classes.name
    LEFT JOIN IsTaking ON Students.id = IsTaking.sid
    LEFT JOIN Classes AS ClassesInProgres ON IsTaking.name = ClassesInProgres.name
    LEFT JOIN Majors ON Students.id = Majors.sid
    LEFT JOIN Departments AS DepartmentsMajors ON Majors.dname = DepartmentsMajors.name
    LEFT JOIN Minors ON Students.id = Minors.sid
    LEFT JOIN Departments AS DepartmentsMinors ON Minors.dname = DepartmentsMinors.name
    GROUP BY Students.id) AS gpa_info
LEFT JOIN 
    (SELECT s.id, SUM(CASE WHEN ht.grade = 'F' THEN 0 ELSE c.credits END) AS total_credits
    FROM Students s
    JOIN HasTaken ht ON s.id = ht.sid
    JOIN Classes c ON ht.name = c.name
    GROUP BY s.id) AS credit_info
ON gpa_info.id = credit_info.id;