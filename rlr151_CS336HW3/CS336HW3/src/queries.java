
public class queries {
	
	public static final String queryQuestionA = "LOWER(Students.first_name) LIKE LOWER('%SUBSTRING%') OR LOWER(Students.last_name) LIKE LOWER('%SUBSTRING%')";
	
	public static final String queryStudentInfoWithCondition = "SELECT gpa_info.id, gpa_info.first_name, gpa_info.last_name, gpa_info.majors, gpa_info.minors, gpa_info.gpa, \r\n" + 
			"       credit_info.total_credits\r\n" + 
			"FROM \r\n" + 
			"    (SELECT Students.id, Students.first_name, Students.last_name,\r\n" + 
			"       GROUP_CONCAT(DISTINCT DepartmentsMajors.name SEPARATOR ', ') AS majors,\r\n" + 
			"       GROUP_CONCAT(DISTINCT DepartmentsMinors.name SEPARATOR ', ') AS minors,\r\n" + 
			"       (SUM(CASE HasTaken.grade\r\n" + 
			"            WHEN 'A' THEN 4\r\n" + 
			"            WHEN 'B' THEN 3\r\n" + 
			"            WHEN 'C' THEN 2\r\n" + 
			"            WHEN 'D' THEN 1\r\n" + 
			"            ELSE 0\r\n" + 
			"        END * Classes.credits) /\r\n" + 
			"       SUM(Classes.credits)) AS gpa\r\n" + 
			"    FROM Students \r\n" + 
			"    LEFT JOIN HasTaken ON Students.id = HasTaken.sid\r\n" + 
			"    LEFT JOIN Classes ON HasTaken.name = Classes.name\r\n" + 
			"    LEFT JOIN IsTaking ON Students.id = IsTaking.sid\r\n" + 
			"    LEFT JOIN Classes AS ClassesInProgres ON IsTaking.name = ClassesInProgres.name\r\n" + 
			"    LEFT JOIN Majors ON Students.id = Majors.sid\r\n" + 
			"    LEFT JOIN Departments AS DepartmentsMajors ON Majors.dname = DepartmentsMajors.name\r\n" + 
			"    LEFT JOIN Minors ON Students.id = Minors.sid\r\n" + 
			"    LEFT JOIN Departments AS DepartmentsMinors ON Minors.dname = DepartmentsMinors.name\r\n" + 
			"    WHERE CONDITION\r\n" + 
			"    GROUP BY Students.id) AS gpa_info\r\n" + 
			"LEFT JOIN \r\n" + 
			"    (SELECT s.id, SUM(CASE WHEN ht.grade = 'F' THEN 0 ELSE c.credits END) AS total_credits\r\n" + 
			"    FROM Students s\r\n" + 
			"    JOIN HasTaken ht ON s.id = ht.sid\r\n" + 
			"    JOIN Classes c ON ht.name = c.name\r\n" + 
			"    GROUP BY s.id) AS credit_info\r\n" + 
			"ON gpa_info.id = credit_info.id;";

	public static final String queryDepartmentInfoWithCondition = "SELECT COUNT(*) AS num_students, AVG(gpa) AS avg_gpa\r\n" + 
			"FROM Students\r\n" + 
			"JOIN Majors ON Students.id = Majors.sid\r\n" + 
			"JOIN (\r\n" + 
			"    SELECT Students.id,\r\n" + 
			"        (SUM(CASE HasTaken.grade\r\n" + 
			"            WHEN 'A' THEN 4\r\n" + 
			"            WHEN 'B' THEN 3\r\n" + 
			"            WHEN 'C' THEN 2\r\n" + 
			"            WHEN 'D' THEN 1\r\n" + 
			"            ELSE 0\r\n" + 
			"        END * Classes.credits) /\r\n" + 
			"       SUM(Classes.credits)) AS gpa\r\n" + 
			"    FROM Students \r\n" + 
			"    LEFT JOIN HasTaken ON Students.id = HasTaken.sid\r\n" + 
			"    LEFT JOIN Classes ON HasTaken.name = Classes.name\r\n" + 
			"    GROUP BY Students.id\r\n" + 
			") AS gpa_info ON Students.id = gpa_info.id\r\n" + 
			"WHERE Majors.dname = DEPARTMENT;";

	public static final String queryPastCourseInfoWithCondition = "SELECT HasTaken.name, \r\n" + 
			"    SUM(CASE WHEN HasTaken.grade = 'A' THEN 1 ELSE 0 END) AS num_A,\r\n" + 
			"    SUM(CASE WHEN HasTaken.grade = 'B' THEN 1 ELSE 0 END) AS num_B,\r\n" + 
			"    SUM(CASE WHEN HasTaken.grade = 'C' THEN 1 ELSE 0 END) AS num_C,\r\n" + 
			"    SUM(CASE WHEN HasTaken.grade = 'D' THEN 1 ELSE 0 END) AS num_D,\r\n" + 
			"    SUM(CASE WHEN HasTaken.grade = 'F' THEN 1 ELSE 0 END) AS num_F\r\n" + 
			"FROM HasTaken\r\n" + 
			"WHERE HasTaken.name = 'COURSENAME'\r\n" + 
			"GROUP BY HasTaken.name;";
	
	public static final String queryCurrentCourseInfoWithCondition = "SELECT name as Course_Name, COUNT(*) as Currently_Enrolled\r\n" + 
			"FROM IsTaking\r\n" + 
			"WHERE IsTaking.name = 'COURSENAME'\r\n" +
			"Group by name;";

}
