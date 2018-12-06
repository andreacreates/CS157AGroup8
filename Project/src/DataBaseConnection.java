import java.sql.*;

import com.mysql.cj.jdbc.CallableStatement;

// Class used for connecting to the Database
public final class DataBaseConnection {
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://35.233.153.166:3306/cs157";

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "group3cs160";

	// SQL Objects
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	public static Connection openConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// creates prepared statement
	public static PreparedStatement createPreparedStatement(String sql) {
		try {
			stmt = conn.prepareStatement(sql);
			return (PreparedStatement) stmt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// executes prepared statement and returns result set
	public static ResultSet executePreparedStatement() {
		PreparedStatement ps = (PreparedStatement) stmt;
		try {
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ResultSet executeCallableStatement() {
		CallableStatement cs = (CallableStatement) stmt;
		try {
			rs = cs.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// executes regular statement and returns result set
	public static ResultSet sqlStatement(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// closes all SQL objects
	public static void closeConnection() {

		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException se2) {
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static CallableStatement prepareCall(String string) throws SQLException {
		conn.prepareCall(string);
		return null;
	}
}
