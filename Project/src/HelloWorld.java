import java.sql.*;

// Example Class how to use JDBC with DataBaseConnection class I created
public class HelloWorld {

	public static void statementExample() {
		DataBaseConnection db = new DataBaseConnection();
		db.openConnection();
		try {
			System.out.println("Model\tBrand\tType");

			ResultSet rs = db.sqlStatement("SELECT * FROM product");

		
			while (rs.next()) {
				// Retrieve by column name
				int model = rs.getInt("model");
				String brand = rs.getString("brand");
				String type = rs.getString("type");

				// Display values
				System.out.print(model);
				System.out.print("\t" + brand);
				System.out.println("\t" + type);
			}

			db.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void preparedStatementExample() {
		DataBaseConnection db = new DataBaseConnection();
		db.openConnection();
		
		// Parameters are (?)
		PreparedStatement stmt = db.createPreparedStatement("INSERT INTO user VALUES(?,?,?,?)");
		try {
			
			// Sets parameters of (?) SQL statement 
			stmt.setInt(1, 25);
			stmt.setString(2, "M");
			stmt.setInt(3, 8);
			stmt.setInt(4, 80);
			
			
			// use executeUpdate when calling INSERT, UPDATE, or DELETE only
			stmt.executeUpdate();
			
			db.closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void argumentExample(String input) {
	
		DataBaseConnection db = new DataBaseConnection();
		db.openConnection();
		
		PreparedStatement stmt = db.createPreparedStatement("SELECT * FROM product WHERE type = ?");
		
		try {
			stmt.setString(1, input);
			
			ResultSet rs = db.executePreparedStatement();
			
			while (rs.next()) {
				// Retrieve by column name
				int model = rs.getInt("model");
				String brand = rs.getString("brand");
				String type = rs.getString("type");

				// Display values
				System.out.print(model);
				System.out.print("\t" + brand);
				System.out.println("\t" + type);
			}

			db.closeConnection();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello World");

		HelloWorld.argumentExample("percussion");	
		HelloWorld.argumentExample("guitar");
		//HelloWorld.statementExample();
		//HelloWorld.preparedStatementExample();
		
		System.out.println("Goodbye!");
	}
}
