SELECT s.first_name, s.last_name, s.id, SUM(c.credits) AS total_credits
FROM Students s
JOIN HasTaken ht ON s.id = ht.sid
JOIN Classes c ON ht.name = c.name
JOIN Majors maj on maj.sid = s.id
WHERE (ht.grade != "F")
GROUP BY s.id;