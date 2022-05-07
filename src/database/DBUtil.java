package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.Set;

import model.Test;
import model.TestCase;
import model.User;

public class DBUtil 
{
	public static Connection getConnection() throws Exception {
		Connection c = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		c = DriverManager.getConnection("jdbc:mysql://database-1.cv9idvaobmel.us-east-1.rds.amazonaws.com:3306/editor?useSSL=false", "root","password");
		return c;
	}
	
	private static DBUtil instance;
	public static DBUtil getInstance() {
	
		if(instance != null) {
			return instance;
		}
		return new DBUtil();
	}
	private DBUtil() {
		// Private constructor for singleton
	}
	
	public User CreateUser(String userId, String userName, String userPassword) throws Exception
	{
		Connection con = null;
		PreparedStatement pt = null;
		User user = null;
		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into User values (?,?,?,?)");
			pt.setString(1, userId);
			pt.setString(2, userName);
			pt.setString(3, userPassword);
			pt.setInt(4, 2);
			pt.executeUpdate();
			user = new User(userId, userName, userPassword, 2);
		}finally {
			if(pt != null) pt.close();
			if(con != null) con.close();
		}
		return user;
	}
	
	public User getUser(String id, String pass) throws Exception
	{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		User user = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Select userId, userName, userPassword, userType from User where userId = ? AND userPassword = ?");
			pt.setString(1, id);
			pt.setString(2, pass);
			rs = pt.executeQuery();
			String p[] = new String[5];
			while (rs.next()) {
				for (int i = 1; i <= 4; i++) {
					p[i] = rs.getString(i);
				}
				String userId = p[1];
				String userName = p[2];
				String userPassword = p[3];
				int userType= Integer.parseInt(p[4]);
				user = new User(userId, userName, userPassword, userType);
			}
		}
		finally {
			if(pt != null) pt.close();
			if(con != null) con.close();
		}
		return user;
	}

	public Integer createTest(String title, String description, String difficulty) throws Exception {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Integer testId = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into Test (testTitle, testDesc, testDiff) values (?,?,?)");
			pt.setString(1, title);
			pt.setString(2, description);
			pt.setString(3, difficulty);
			pt.executeUpdate();
			if(pt != null) pt.close();
			pt = con.prepareStatement("select last_insert_id();");
			rs = pt.executeQuery();
			rs.next();
			testId = rs.getInt(1);
		} finally {
			if(pt != null) pt.close();
			if(con != null) con.close();		
		}
		return testId;
	}

	public void addTestCase(String input, String output, int testId, int testCaseType) throws Exception {
		
		Connection con = null;
		PreparedStatement pt = null;
		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into TestCase (input, output, testId, testCaseType) values (?,?,?,?)");
			pt.setString(1, input);
			pt.setString(2, output);
			pt.setInt(3, testId);
			pt.setInt(4, testCaseType);
			pt.executeUpdate();
		}finally {
			if(pt != null) pt.close();
			if(con != null) con.close();
		}	
	}

	public Set<Test> getTests() throws Exception {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<Test> tests = null;

		try {
			tests = new LinkedHashSet<Test>();
			con = getConnection();
			pt = con.prepareStatement("Select * from Test");
			rs = pt.executeQuery();
			String p[] = new String[6];
			while (rs.next()) 
			{
				for (int i = 1; i <= 4; i++) 
				{
					p[i] = rs.getString(i);
				}
				int testId = Integer.parseInt(p[1]);
				String testTitle = p[2];
				String testDesc = p[3];
				String testDiff = p[4];

				TestCase publicTc = getPublicTestCase(testId);
				Test test = new Test(testId, testTitle, testDesc, testDiff, publicTc);
				tests.add(test);
				
			}
		}finally {
			if(rs != null) rs.close();
			if(pt != null) pt.close();
			if(con != null) con.close();
		}
		return tests;
	}

	private TestCase getPublicTestCase(int testId) throws Exception{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		TestCase testcase = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from TestCase where testId = ? AND testCaseType = '1'");
			pt.setInt(1, testId);
			rs = pt.executeQuery();
			String p[] = new String[5];
			rs.next();
			for (int i = 1; i <= 3; i++) 
			{
				p[i] = rs.getString(i);
			}
			int testCaseId = Integer.parseInt(p[1]);
			String input = p[2];
			String output = p[3];
			testcase = new TestCase(testCaseId, input, output);
		}finally {
			if(rs != null) rs.close();
			if(pt != null) pt.close();
			if(con != null) con.close();
		}
		return testcase;
	}

	private Set<TestCase> getTestCases(int testId) throws Exception {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Set<TestCase> testcases = null;

		try {
			testcases = new LinkedHashSet<TestCase>();
			con = getConnection();
			pt = con.prepareStatement("Select * from TestCase where testId = ? AND testCaseType = '2'");
			pt.setInt(1, testId);
			rs = pt.executeQuery();
			String p[] = new String[5];
			while (rs.next()) 
			{
				for (int i = 1; i <= 3; i++) 
				{
					p[i] = rs.getString(i);
				}
				int testCaseId = Integer.parseInt(p[1]);
				String input = p[2];
				String output = p[3];
				testcases.add(new TestCase(testCaseId, input, output));
			}
		}finally {
			if(rs != null) rs.close();
			if(pt != null) pt.close();
			if(con != null) con.close();
		}
		return testcases;
	}

	public Test getTest(int tId, boolean getHiddenTestCases) throws Exception{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Test test = null;

		try {
			con = getConnection();
			pt = con.prepareStatement("Select * from Test where testId = ?");
			pt.setInt(1,tId);
			rs = pt.executeQuery();
			rs.next();
			String p[] = new String[6];
			for (int i = 1; i <= 4; i++) 
			{
				p[i] = rs.getString(i);
			}
			int testId = Integer.parseInt(p[1]);
			String testTitle = p[2];
			String testDesc = p[3];
			String testDiff = p[4];
			TestCase publicTc = getPublicTestCase(testId);
			test = new Test(testId, testTitle, testDesc, testDiff, publicTc);
			if(getHiddenTestCases) {
				Set<TestCase> testcases = getTestCases(testId);
				test.setTestcases(testcases);
			}
		} finally {
			if(rs != null) rs.close();
			if(pt != null) pt.close();
			if(con != null) con.close();
		}
		return test;
	}
}
