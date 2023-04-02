SELECT HasTaken.name, 
    SUM(CASE WHEN HasTaken.grade = 'A' THEN 1 ELSE 0 END) AS num_A,
    SUM(CASE WHEN HasTaken.grade = 'B' THEN 1 ELSE 0 END) AS num_B,
    SUM(CASE WHEN HasTaken.grade = 'C' THEN 1 ELSE 0 END) AS num_C,
    SUM(CASE WHEN HasTaken.grade = 'D' THEN 1 ELSE 0 END) AS num_D,
    SUM(CASE WHEN HasTaken.grade = 'F' THEN 1 ELSE 0 END) AS num_F
FROM HasTaken
WHERE HasTaken.name = 'Accounting for Entrepreneurs'
GROUP BY HasTaken.name;
