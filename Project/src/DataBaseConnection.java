import java.sql.*;


// Class used for connecting to the Database
public class DataBaseConnection {
	// JDBC driver name and database URL
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://35.233.153.166:3306/cs157";

	// Database credentials
	private final String USER = "root";
	private final String PASS = "group3cs160";
	
	// SQL Objects
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	public Connection openConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected!!");
			return conn;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// creates prepared statement
	public PreparedStatement createPreparedStatement(String sql) {
		try {
			stmt = conn.prepareStatement(sql);
			return (PreparedStatement) stmt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// executes prepared statement and returns result set
	public ResultSet executePreparedStatement() {
		PreparedStatement ps = (PreparedStatement) stmt;
		try {
			rs =  ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// executes regular statement and returns result set
	public ResultSet sqlStatement(String sql) {
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
	public void closeConnection() {
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
}
