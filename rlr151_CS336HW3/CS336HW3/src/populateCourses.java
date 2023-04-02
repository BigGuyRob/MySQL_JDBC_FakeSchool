import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The purpose of this class is to fill the SQL Classes table with the random course data I created
 * and assign either 3 or 4 credits to each Class
 * @author rreid
 *
 */
public class populateCourses {

	/**
	 * Accesses global_course_list.courseList and its public static methods to generate the ArrayList
	 * @param args - Unused
	 * @throws SQLException
	 */
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs336hw3","rob","rob");
        global_course_list.addCoursesToList();
        ArrayList<String> courseList = global_course_list.courseList;
        
        for (String c : courseList) {
            String s = "INSERT INTO Classes VALUES(?,?)";
            PreparedStatement p = conn.prepareStatement(s);
            p.setString(1, c);
            Random rand = new Random();
            int randomNumber = rand.nextInt(2) + 3;
            p.setInt(2, randomNumber);
            p.executeUpdate();
        }
        conn.close();
    }
}
