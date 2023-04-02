SELECT COUNT(*) AS num_students, AVG(gpa) AS avg_gpa
FROM Students
JOIN Majors ON Students.id = Majors.sid
JOIN (
    SELECT Students.id,
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
    GROUP BY Students.id
) AS gpa_info ON Students.id = gpa_info.id
WHERE Majors.dname = 'CS';