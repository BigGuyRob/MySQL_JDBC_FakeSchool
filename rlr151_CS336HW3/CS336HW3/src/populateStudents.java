import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The purpose of this class to enter into the Students table 100 new students
 * @author rreid
 *
 */
public class populateStudents {
	private static String[] first_names = {"Bob", "Joe", "Rob", "Bill", "Adam", "Dan", "Greg", "William", "Kate", "Mary"};
	private static String[] last_names = {"Smith", "Chair", "Keyboard", "Clip", "Froth", "Cheap", "Paper", "Stick", "Goodman", "Wooden"};
	
	/**
	 * This method matches up the 10 first and 10 last names to make 100 unique student names
	 * Every entered name is assumed unique before entry so no errors are possible
	 * @param args
	 * @throws SQLException
	 */
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs336hw3","rob","rob");
        int ID = 192000000;
        for(int first = 0; first < 10; first++) {
        	for(int last = 0; last < 10; last++) {
        		String s = "INSERT INTO Students VALUES(?,?,?)";
        		PreparedStatement p = conn.prepareStatement(s);
                p.setString(1, first_names[first]);
                p.setString(2, last_names[last]);
                p.setString(3, Integer.toString(ID));
                p.executeUpdate();
                ID += 1;
        	}
        }
        
        conn.close();
    }
}