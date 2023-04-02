import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class Problem3Interface {
	private static Connection conn;
	private static Dictionary<String, String> majors = new Hashtable<>();
	private static Dictionary<String, String> minors = new Hashtable<>();
	private static ArrayList<String> courseList = new ArrayList<String>();
	
	/**
	 * The main method scans for user input and can provide the operations
	 * Here I map the operations from letter code to the written public methods.
	 * Public because I wanted to run these from another class as an API
	 * @param args - Unused
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs336hw3","rob","rob");
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		boolean valid = false;
		String[] Operations = {"a. Search students by name", "b. Search students by year (Fr, So, Ju, Sr)", "c. Search for students with a GPA equal to or above a given threshold", "d. Search for students with a GPA equal to or below a given threshold", "e. or a given department, report its number of students and the average of\r\n" + 
				"those students’ GPAs", "f. For a given class, report the number of students currently taking it. Also,\r\n" + 
						"among students who’ve taken the class, show the number of students who’ve\r\n" + 
						"gotten each letter grade" , "g. Execute an abitrary SQL query"};
        char code;
        
        do {
            System.out.print("Enter a character code (a-g which are the parts of question 3) \n");
            System.out.print("h to list operations and x to exit: \n");
            code = scanner.nextLine().toLowerCase().charAt(0);
            switch (code) {
            case 'a':
                System.out.println(Operations[0]);
                System.out.println("Please input the string you wish to search for");
                String name = scanner.nextLine();
                qA(name);
                break;
            case 'b':
                System.out.println(Operations[1]);
                String input;
                do {
                    System.out.print("Enter a value (FR, SO, JU, SR): ");
                    input = scanner.nextLine().toUpperCase();
                    if (input.equals("FR") || input.equals("SO") || input.equals("JU") || input.equals("SR")) {
                        valid = true;
                    } else {
                        System.out.println("Invalid input. Please enter a value from the list (FR, SO, JU, SR).");
                    }
                } while (!valid);
                qB(input);
                break;
            case 'c':
            	System.out.println(Operations[2]);
                String gpa;
                boolean isInt = false;
                do {
                	gpa = scanner.nextLine();
                	try {
                        Double.parseDouble(gpa);
                        isInt = true;
                    } catch (NumberFormatException e) {
                        isInt = false;
                    }
                } while (!isInt);
                qC(gpa);
                break;
            case 'd':
            	System.out.println(Operations[3]);
                String gpa_l;
                boolean isInt_l = false;
                do {
                	gpa_l = scanner.nextLine();
                	try {
                        Double.parseDouble(gpa_l);
                        isInt_l = true;
                    } catch (NumberFormatException e) {
                        isInt_l = false;
                    }
                } while (!isInt_l);
                qD(gpa_l);
                break;
            case 'e':
                System.out.println(Operations[4]);
                String[] majors = {"BIO", "CHEM", "CS", "ENG", "MATH", "PHYS"};
                System.out.print("Enter major (\"BIO\", \"CHEM\", \"CS\", \"ENG\", \"MATH\", \"PHYS\"): ");
                boolean validDepartment = false;
                String department = "";
	            do {
	            	department = scanner.nextLine().toUpperCase();
	                for(String dept : majors) {
	                	if(dept.contentEquals(department)) {
	                		validDepartment = true;
	                	}
	                }
                }while(!validDepartment);
	            qE(department);
                break;
            case 'f':
                System.out.println(Operations[5]);
                System.out.println("L: List all course names");
                getCourses();
                boolean validCourse = false;
                String course = "";
	            do {
	            	course = scanner.nextLine().toUpperCase();
	            	if(course.contentEquals("L")) {for(String c : courseList) {System.out.println(c);}}
	            	
	                for(String f : courseList) {
	                	f = f.toUpperCase();
	                	if(f.contentEquals(course)) {
	                		validCourse = true;
	                	}
	                }
	                if(!validCourse) {
	                	System.out.println("Please select a valid course and enter 'L' to list courses");
	                }
                }while(!validCourse);
	            qF(course);
                break;
            case 'g':
                System.out.println(Operations[6]);
                System.out.println("ENTER YOUR QUERY");
                String query = "";
                do {
                	query = scanner.nextLine().toUpperCase();
                }while(query.contentEquals(""));
                qG(query);
                break;
            case 'h':
                System.out.println("help");
                for(String s : Operations) {
                	System.out.println(s);
                	System.out.println(" ");
                }
                break;
            case 'x':
                System.out.println("exit");
                exit = true;
                break;
            default:
                System.out.println("Invalid code selected");
                break;
        }
        
        
        } while (exit == false && (code == 'a' || code == 'b' || code == 'c' || code == 'd' || code == 'e' || code == 'f' || code == 'g' || code == 'h' || code == 'x'));
        
        System.out.println("Invalid Input " + code);
        System.out.println("Program ended think about this next time");
        scanner.close();
	}
	
	/**
	 * This method executes a general query and returns the record count
	 * @param query
	 * @throws SQLException
	 */
	public static void executeQueryWithCondition(String query) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(query);
		int count = 0;
		while(rs.next()) {count +=1;}
		rs = state.executeQuery(query);
		
		while(rs.next()){
			
			String first_name = "";
			String last_name = "";
			String ID = "";
			String Credits = "";
			String Major = "";
			String Minor = "";
			String GPA = "";
			ID = rs.getString(1);
			first_name = rs.getString(2);
			last_name = rs.getString(3);
			Major = rs.getString(4);
			Minor = rs.getString(5);
			GPA = rs.getString(6);
			Credits = rs.getString(7);
			printStudentFormat(first_name, last_name, ID, Major, Minor, Credits, GPA);
		}
		System.out.println(count + " Records Found");
	}
	
	
	/**
	 * This is the code for question A. It takes an input which can be any substring that we wish to search from
	 * the students table
	 * @param input - String, substring to search for
	 * @throws SQLException
	 */
	static void qA(String input) throws SQLException {
		//search for students by name
		String q = queries.queryStudentInfoWithCondition;
		q = q.replace("CONDITION", "(" + queries.queryQuestionA+")");
		q = q.replace("SUBSTRING", input);
		executeQueryWithCondition(q);
	}
	
	/**
	 * This is the code for question B. It takes an input which can be any year that we wish to search from
	 * from the students table
	 * @param year - String, substring to search for validated to be Fr, So, Ju, Sr in calling method
	 * @throws SQLException
	 */
	static void qB(String year) throws SQLException {
		//search for students by name
		Statement state = conn.createStatement();
		String q = queries.queryStudentInfoWithCondition;
		String cond = "";	
		if (year.equals("FR")) {
		    cond = "credit_info.total_credits <= 30";
		} else if (year.equals("SO")) {
			cond = "credit_info.total_credits > 30 AND credit_info.total_credits <= 60";
		} else if (year.equals("JU")) {
			cond = "credit_info.total_credits > 60 AND credit_info.total_credits <= 90";
		} else if (year.equals("SR")){
			cond = "credit_info.total_credits > 90";
		}
			q = q .replace("CONDITION", "True");
			q = q.replace(";", " WHERE " + cond + ";");
			executeQueryWithCondition(q);
				
	}

	
	/**
	 * This is the code for question C. It takes an input which can be any gpa that we wish to search from 
	 * from the returned tabe
	 * @param GPA - String, to search for validated to be a double in calling method
	 * @throws SQLException
	 */
	static void qC(String gpa) throws SQLException {
		Statement state = conn.createStatement();
		String q = queries.queryStudentInfoWithCondition;
		String cond = "gpa_info.gpa >= " + gpa;
		q = q .replace("CONDITION", "True");
		q = q.replace(";", " WHERE " + cond + ";");
			executeQueryWithCondition(q);
	}
	

	/**
	 * This is the code for question D. It takes an input which can be the gpa that we wish to search from
	 * the students table. Returning only records below or equal to the threshold
	 * @param gpa
	 * @throws SQLException
	 */
	static void qD(String gpa) throws SQLException {
		Statement state = conn.createStatement();
		String q = queries.queryStudentInfoWithCondition;
		String cond = "gpa_info.gpa <= " + gpa;
		q = q .replace("CONDITION", "True");
		q = q.replace(";", " WHERE " + cond + ";");
		executeQueryWithCondition(q);
	}
	

	/**
	 * This method returns department information using the queryDepartmentInfoWithCondition string
	 * @param department - Department to search for - Validated to be the 6 given departments from 
	 * the homework document in the calling method
	 * @throws SQLException
	 */
	static void qE(String department) throws SQLException{
		Statement state = conn.createStatement();
		String q = queries.queryDepartmentInfoWithCondition;
		q = q .replace("DEPARTMENT", "'" + department + "'");
		ResultSet rs = state.executeQuery(q);
		String num_students = "";
		String avg_gpa = "";
		while(rs.next()) {
			num_students = rs.getString(1);
			avg_gpa = rs.getString(2);
		}
		System.out.println("Num Students: " + num_students);
		System.out.println("Average GPA: " + avg_gpa);
	}

	
	/**
	 * This method queries the HasTaken and IsTaking tables to gather data for a given course
	 * and returns the numbers of A-F grades and those currently enrolled. I do this using the queryCurrentCourseInfoWithCondition
	 * and queryPastCourseInfoWithCondition from the queries class
	 * @param courseName - course we wish to search for validated to be a name from the classes table in the calling method
	 * @throws SQLException
	 */
	static void qF(String courseName) throws SQLException{
		Statement state = conn.createStatement();
		String q = queries.queryCurrentCourseInfoWithCondition;
		q = q .replace("COURSENAME", "" + courseName + "");
		ResultSet rs = state.executeQuery(q);
		String course_name = "";
		String numberCurrentlyEnrolled = "";
		while(rs.next()) {
			course_name = rs.getString(1);
			numberCurrentlyEnrolled = rs.getString(2);
		}
		System.out.println(numberCurrentlyEnrolled + " students currently rolled");
		q = queries.queryPastCourseInfoWithCondition;
		q = q .replace("COURSENAME", "" + courseName + "");
		rs = state.executeQuery(q);
		course_name = "";
		String num_As = "";
		String num_Bs = "";
		String num_Cs = "";
		String num_Ds = "";
		String num_Fs = "";
		while(rs.next()) {
			course_name = rs.getString(1);
			num_As = rs.getString(2);
			num_Bs = rs.getString(3);
			num_Cs = rs.getString(4);
			num_Ds = rs.getString(5);
			num_Fs = rs.getString(6);
		}
		System.out.println("Past Results:");
		System.out.println("A: " + num_As);
		System.out.println("B: " + num_Bs);
		System.out.println("C: " + num_Cs);
		System.out.println("D: " + num_Ds);
		System.out.println("F: " + num_Fs);
	}
	
	/**
	 * This method executes the input string as an SQL query and attempts to decode the result using
	 * ResultSetMetaData to determine the columns
	 * @param query - String - query to be executed
	 * @throws SQLException
	 */
	static void qG(String query) throws SQLException{
		Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData metadata = rs.getMetaData();
        
        int numColumns = metadata.getColumnCount();
        System.out.println("Number of columns: " + numColumns);
        
        for (int i = 1; i <= numColumns; i++) {
            String columnName = metadata.getColumnName(i);
            String columnType = metadata.getColumnTypeName(i);
            System.out.println("Column " + i + ": " + columnName + " (" + columnType + ")");
        }
        for (int i = 1; i <= numColumns; i++) {
            System.out.print(metadata.getColumnName(i) + "\t");
        }
        System.out.println();

        // Print out the rows
        while (rs.next()) {
            for (int i = 1; i <= numColumns; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
	}
	
	/**
	 * This method queries and returns an arrayList of all of the available course data
	 * @throws SQLException
	 */
	static void getCourses() throws SQLException {
		Statement state = conn.createStatement();
		String q = "SELECT name from classes";
		ResultSet rs = state.executeQuery(q);
		String course_name = "";
		while(rs.next()) {
			course_name = rs.getString(1);
			courseList.add(course_name);
		}
	}
	
	static void printStudentFormat(String first_name, String last_name, String ID, String major, String minor, String credits, String GPA) {
		System.out.println(last_name + ", " + first_name);
		System.out.println("ID: " + ID);
		if(major == null) { major = "";}
		if(minor == null) {minor = "";}
		System.out.println("Major: " + major);
		System.out.println("Minor: " + minor);
		System.out.println("GPA: " + GPA);
		System.out.println("Credits: " + credits);
		System.out.println("");
	}
}
