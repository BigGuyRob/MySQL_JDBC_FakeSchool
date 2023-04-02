import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The purpose of this class is to enter the 6 departments given in the homework document into the table
 * it also randomizes the campus that the department is located on from the given campuses
 * @author rreid
 *
 */
public class populateDepartments {

	/**
	 * The main method generates a random number 1-4 to choose for each campus 
	 * then inserts them using prepared statements. 
	 * @param args
	 * @throws SQLException
	 */
    public static void main(String[] args) throws SQLException {
        ArrayList<String> deptList = new ArrayList<String>();
        deptList.add("BIO");
        deptList.add("CHEM");
        deptList.add("CS");
        deptList.add("ENG");
        deptList.add("MATH");
        deptList.add("PHYS");
        String[] campus = {"Busch", "CAC", "Livi", "CD"};
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs336hw3","rob","rob");
        for (String c : deptList) {
            String s = "INSERT INTO Departments VALUES(?,?)";
            PreparedStatement p = conn.prepareStatement(s);
            p.setString(1, c);
            Random rand = new Random();
            int randomNumber = rand.nextInt(4); //I just want to assign a random campus to each departmnet
            p.setString(2, campus[randomNumber]);
            p.executeUpdate();
        }
        conn.close();
    }
}
