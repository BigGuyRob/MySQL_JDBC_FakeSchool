SELECT name as Course_Name, COUNT(*) as Currently_Enrolled
FROM IsTaking
Group by name;