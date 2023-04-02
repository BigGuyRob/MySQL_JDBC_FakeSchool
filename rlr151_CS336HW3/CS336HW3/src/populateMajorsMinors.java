import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * The purpose of this class is fill the Majors and Minors tables with assigned student data
 * @author rreid
 *
 */
public class populateMajorsMinors { //also does minors
	private static Connection conn;
	
	/**
	 * The main method loops through the IDs and for each student decides 1 of 4 cases 
	 * using a randomly generated integer. This method is responsible for generating the unique random numbers
	 * to ensure that no one takes the same course multiple times or takes a course they have taken before. 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		String[] majors = {"BIO", "CHEM", "CS", "ENG", "MATH", "PHYS"};
		String[] minors = {"BIO", "CHEM", "CS", "ENG", "MATH", "PHYS"};
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs336hw3","rob","rob");
        int ID = 192000000;
        //some students will have a major and a minor
        //some will only have a major
        //some will only have a minor
        //some will have neither
        //4 cases
        for(int c = 0; c < 100; c++) {
        	Random rand = new Random();//case number
        	int cs = rand.nextInt(4) + 1; //1-4
        	int major_s = rand.nextInt(6);//0-5
        	int minor_s = major_s;
        	while(minor_s == major_s) {
        		minor_s = rand.nextInt(6);//0-5
        	}
        	//they cant be the same
        	switch(cs) {
        	case 1: //major and a minor
        		insertMajorMinor(Integer.toString(ID), majors[major_s], minors[minor_s]);
        		break;
        	case 2: //major
        		insertMajor(Integer.toString(ID), majors[major_s]);
        		break;
        	case 3: //I want if the student only has a minor for them to have two
        		insertMinor(Integer.toString(ID), minors[minor_s]);
        		insertMinor(Integer.toString(ID), minors[major_s]);
        		break;
        	case 4:
        		//nothing
        		insertMajor(Integer.toString(ID), majors[major_s]);
        		insertMajor(Integer.toString(ID), majors[minor_s]);
        		break;
        	}     
        	ID += 1;
        }
        
        conn.close();
    }
	
	/**
	 * The Major Minor Method adds a major and a minor for the selected student
	 * @param ID
	 * @param minordname
	 * @param majordname
	 * @throws SQLException
	 */
	public static void insertMajorMinor(String ID, String minordname, String majordname) throws SQLException {
		String s = "INSERT INTO Majors VALUES(?,?)";
		PreparedStatement maj = conn.prepareStatement(s);
        maj.setString(1, ID);
        maj.setString(2, majordname);
        maj.executeUpdate();
        
        s = "INSERT INTO Minors VALUES(?,?)";
		PreparedStatement min = conn.prepareStatement(s);
        min.setString(1, ID);
        min.setString(2, minordname);
        min.executeUpdate();
	}
	
	/**
	 * The Single Major method adds only a major for the selected student
	 * @param ID
	 * @param majordname
	 * @throws SQLException
	 */
	public static void insertMajor(String ID, String majordname) throws SQLException {
		String s = "INSERT INTO Majors VALUES(?,?)";
		PreparedStatement maj = conn.prepareStatement(s);
        maj.setString(1, ID);
        maj.setString(2, majordname);
        maj.executeUpdate();
	}
	
	/**
	 * The Single Minor method adds only a minor for the selected student
	 * @param ID
	 * @param minordname
	 * @throws SQLException
	 */
	public static void insertMinor(String ID, String minordname) throws SQLException {
		String s = "INSERT INTO Minors VALUES(?,?)";
		PreparedStatement min = conn.prepareStatement(s);
        min.setString(1, ID);
        min.setString(2, minordname);
        min.executeUpdate();
	}
}
