use cs336hw3;

SELECT s.id, ((SUM(CASE h.grade
                        WHEN 'A' THEN 4 * c.credits
                        WHEN 'B' THEN 3 * c.credits
                        WHEN 'C' THEN 2 * c.credits
                        WHEN 'D' THEN 1 * c.credits
                        ELSE 0
                    END)
            ) / SUM(c.credits)) AS GPA
FROM Students s
INNER JOIN HasTaken h ON s.id = h.sid
INNER JOIN Classes c ON h.name = c.name
GROUP BY s.id;