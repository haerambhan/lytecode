package Database;
import java.sql.*;
import Model.*;
import java.util.*;

public class DatabaseAccess 
{
	public static Connection getConnection() throws SQLException {
		Connection c = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/editor?useSSL=false", "root",
					"root");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw e;
		}
		return c;
	}
	
	public User CreateUser(String userId, String userName, String userPassword) throws SQLException
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
			return user;
		} catch (SQLException e) {
			System.out.println(e);
			throw e;
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public User getUser(String id, String pass) throws SQLException
	{
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		User user = null;

		try {
			con = getConnection();
			pt = con.prepareStatement(
					"Select userId, userName, userPassword, userType from User where userId = ? AND userPassword = ?");
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
				return user;
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL Exception has occured");
			throw e;
		}
		finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return null;
	}

	public int createTest(String title, String description, String difficulty) throws SQLException {
		Connection con = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		System.out.println("CREATE TEST");

		try {
			con = getConnection();
			pt = con.prepareStatement("Insert into Test (testTitle, testDesc, testDiff) values (?,?,?)");
			pt.setString(1, title);
			pt.setString(2, description);
			pt.setString(3, difficulty);
			pt.executeUpdate();
			pt.close();
			pt = con.prepareStatement("select last_insert_id();");
			rs = pt.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			return id;
		} catch (SQLException e) {
			System.out.println(e);
			throw e;
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void addTestCase(String input, String output, int testId, int testCaseType) throws SQLException {
		
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
		} catch (SQLException e) {
			System.out.println(e);
			throw e;
		} finally {
			try {
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}	
	}

	public Set<Test> getTests() {
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
				Set<TestCase> testcases = getTestCases(testId);
				tests.add(new Test(testId, testTitle, testDesc, testDiff, testcases, publicTc));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return tests;
	}

	private TestCase getPublicTestCase(int testId) {
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
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return testcase;
	}

	private Set<TestCase> getTestCases(int testId) {
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
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return testcases;
	}

	public Test getTest(int tId) {
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
			Set<TestCase> testcases = getTestCases(testId);
			TestCase publicTc = getPublicTestCase(testId);
			test = new Test(testId, testTitle, testDesc, testDiff, testcases, publicTc);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pt.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return test;
	}
}
