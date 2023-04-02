import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * The purpose of this method is to generate random data for the HasTaken and IsTaking tables
 * The main idea here is that the student cannot take the same course multiple times or 
 * be taking the same course multiple times.
 * @author rreid
 *
 */
public class populateTakenTaking {
	private static Connection conn;
	private static ArrayList<String> courseList;
	
	
	
	//The result of this method should be all 100 students taking any 3 unique classes and then any number of past courses that are NOT those they are currently taking
	/**
	 * The main method makes calls to global_course_list and its public static methods in order
	 * to obtain the course list. In the interface for problem 3 this is omitted, and no data is stored locally.
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs336hw3","rob","rob");
        global_course_list.addCoursesToList();
        courseList = global_course_list.courseList;
      //name of course must come from course list which can be queried but I have it saved
        
        int ID = 192000000;
        
        //4 cases
        for(int c = 0; c < 100; c++) {
        	Random rand = new Random();//case number
        	//every student is taking or has taken some courses. or else they wouldn't be a student
        	//Im going to generate a number of courses and 3 current courses per student
        	//a random index to get from courseList
        	int size = courseList.size();
            int count = 3; // I want 3 random unique courses
            Set<Integer> uniqueNumbers = new HashSet<Integer>();
            while (uniqueNumbers.size() < 3) {
                int number = rand.nextInt(courseList.size()); // generate a random number
                uniqueNumbers.add(number); // add the number to the set
                
            }
            Integer[] numbersArray = uniqueNumbers.toArray(new Integer[0]);
            addCurrentCourses(ID, numbersArray[0], numbersArray[1], numbersArray[2]);
            
        	ID += 1;
        }
        
        conn.close();
    }
	
	/**
	 * Add current courses generates 3 unique random course IDs and makes calls to AddCurrentCourse which 
	 * inserts them via SQL insertion. 
	 * @param ID
	 * @param course1
	 * @param course2
	 * @param course3
	 * @throws SQLException
	 */
	static void addCurrentCourses(int ID, int course1, int course2, int course3) throws SQLException {	
        //needs to be 3 random integers that are not the same numbers
        //now add them all to the students taken courses
        addCurrentCourse(Integer.toString(ID), course1);
        addCurrentCourse(Integer.toString(ID), course2);
        addCurrentCourse(Integer.toString(ID), course3);
        //FR Credits = 29/3 = between 0 and 9 courses
        //SO Credits = 30 - 59 = between 10 and 20
        //JU Credits = 60-89 = 21 - 30
        //SR Credits = 90/3 = > 31 courses
        //heres where things get tricky, gotta add a certain number of courses
        Random rand = new Random();
		double num = rand.nextGaussian() * 1.2 + 3; // generates a random number with a mean and std dev
        String year = "";
        int size = 0;
        if (num >= 4.0) {
            year = "SR";
            size = rand.nextInt(1); 
        } else if (num >= 3.0) {
            year = "JU";
            size = rand.nextInt(10) + 21 ;
        } else if (num >= 2.0) {
            year = "SO";
            size = rand.nextInt(10) + 11 ;
        } else if (num >= 1.0) {
            year = "FR";
            size = rand.nextInt(10) + 1 ;
        } else {
        	size = rand.nextInt(10) + 1 ;
            year = "FR";
        }
        int amount_of_courses = courseList.size();
        Set<Integer> set = new HashSet<>();
        set.add(course1);
        set.add(course2);
        set.add(course3);
        while (set.size() < size) {
            set.add(rand.nextInt(amount_of_courses));//any of the courses
        }
        
        Integer[] numbersArray = set.toArray(new Integer[0]);
        //now they cannot take the same course multiple times
        for (int i : numbersArray) {
        	addPastCourse(Integer.toString(ID), i);
        }
	}
	
	/**
	 * Prepares a statement with the course name and student ID to insert into the IsTaking table
	 * @param ID
	 * @param course_num
	 * @throws SQLException
	 */
	private static void addCurrentCourse(String ID, int course_num) throws SQLException {
		String s = "INSERT INTO IsTaking VALUES(?,?)";
		PreparedStatement min = conn.prepareStatement(s);
		min.setString(1, ID);
		min.setString(2, courseList.get(course_num));
		min.executeUpdate();
	}
	
	/**
	 * The add past course method generates a bell curve of data using the 
	 * next gaussian method to assign an A-F grade for each past course
	 * and then inserts this into the HasTaken table
	 * @param ID
	 * @param course_num
	 * @throws SQLException
	 */
	private static void addPastCourse(String ID, int course_num) throws SQLException {
		String s = "INSERT INTO HasTaken VALUES(?,?,?)";
		PreparedStatement min = conn.prepareStatement(s);
		min.setString(1, ID);
		min.setString(2, courseList.get(course_num));
		//here I want a bell curve of A B C D F
		Random rand = new Random();
		double num = rand.nextGaussian() * 2 + 3.5; // generates a random number with a mean and std dev
        String grade = "";
        if (num >= 4.0) {
            grade = "A";
        } else if (num >= 3.0) {
            grade = "B";
        } else if (num >= 2.0) {
            grade = "C";
        } else if (num >= 1.0) {
            grade = "D";
        } else {
            grade = "F";
        }
        min.setString(3, grade);
		min.executeUpdate();
	}
}
