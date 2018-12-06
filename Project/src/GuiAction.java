import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.cj.jdbc.CallableStatement;

public class GuiAction {

	public List<ProductPojo> getProducts() {

		ArrayList<ProductPojo> ret = new ArrayList<>();
		DataBaseConnection.openConnection();

		ResultSet rs = DataBaseConnection.sqlStatement("SELECT * FROM product");

		try {
			while (rs.next()) {
				int model = rs.getInt("model");
				String brand = rs.getString("brand");
				String type = rs.getString("type");
				int views = rs.getInt("views");
				int bought = rs.getInt("bought");

				ret.add(new ProductPojo(brand, model, type, bought, views));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DataBaseConnection.closeConnection();
		return ret;

	}

	public List<SalePojo> getSales() {

		ArrayList<SalePojo> ret = new ArrayList<>();
		DataBaseConnection.openConnection();

		ResultSet rs = DataBaseConnection.sqlStatement("SELECT * FROM sales");

		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int user = rs.getInt("user");
				int item = rs.getInt("item");
				int amount = rs.getInt("amount");

				ret.add(new SalePojo(id, user, item, amount));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DataBaseConnection.closeConnection();
		return ret;

	}

	public List<UserPojo> getUsers() {

		ArrayList<UserPojo> ret = new ArrayList<>();
		DataBaseConnection.openConnection();

		ResultSet rs = DataBaseConnection.sqlStatement("SELECT * FROM user");

		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String gender = rs.getString("gender");
				int age = rs.getInt("age");
				float browseTime = rs.getFloat("browseTime");

				ret.add(new UserPojo(id, gender, age, browseTime));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DataBaseConnection.closeConnection();
		return ret;

	}

	public String getProductDetails(ProductPojo p) throws SQLException {

		String type = p.getType().toLowerCase();
		int model = p.getModel();

		DataBaseConnection.openConnection();

		CallableStatement stmt = DataBaseConnection.prepareCall("SELECT * FROM combine_product WHERE model = ?");
		try {
			stmt.setInt(1, model);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {

				if (type.equals("guitar")) {
					String ret = "Price: " + res.getFloat("price") + "\n" + "Type: " + res.getString("kind") + "\n"
							+ "Size: " + res.getString("size") + "\n";

					DataBaseConnection.closeConnection();
					return ret;
				}

				else if (type.equals("bass")) {
					String ret = "Price: " + res.getFloat("price") + "\n" + "Type: " + res.getString("kind") + "\n";

					DataBaseConnection.closeConnection();
					return ret;
				}

				else if (type.equals("percussion")) {

					String ret = "Price: " + res.getFloat("price") + "\n" + "Type: " + res.getString("kind") + "\n"
							+ "Pieces: " + res.getString("pieces") + "\n";

					DataBaseConnection.closeConnection();
					return ret;

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Error";
	}

	public boolean deleteProduct(int model) throws SQLException {
		DataBaseConnection.openConnection();
		CallableStatement stmt = DataBaseConnection.prepareCall("DELETE FROM product WHERE model = ?");

		try {
			stmt.setInt(1, model);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			DataBaseConnection.closeConnection();
			return false;
		}
		DataBaseConnection.closeConnection();
		return true;
	}

	public boolean addGuitar(String brand, int model, double price, String kind, String size) throws SQLException {

		DataBaseConnection.openConnection();
		CallableStatement stmt = DataBaseConnection.prepareCall("CALL add_guitar(?,?,?,?,?)");
		try {
			stmt.setString(1, brand);
			stmt.setInt(2, model);
			stmt.setDouble(3, price);
			stmt.setString(4, kind);
			stmt.setString(5, size);
			DataBaseConnection.executeCallableStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			DataBaseConnection.closeConnection();
			return false;
		}

		DataBaseConnection.closeConnection();
		return true;
	}

	public boolean addBass(String brand, int model, double price, String kind) throws SQLException {
		
		 
		DataBaseConnection.openConnection();
		CallableStatement stmt = DataBaseConnection.prepareCall("CALL add_bass(?,?,?,?)");
		//PreparedStatement stmt = DataBaseConnection.createPreparedStatement("CALL add_bass(?,?,?,?)");
		try {
			stmt.setString(1, brand);
			stmt.setInt(2, model);
			stmt.setDouble(3, price);
			stmt.setString(4, kind);
			DataBaseConnection.executeCallableStatement();
			//DataBaseConnection.executePreparedStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			DataBaseConnection.closeConnection();
			return false;
		}

		DataBaseConnection.closeConnection();
		return true;
	}

	public boolean addPercussion(String brand, int model, double price, String kind, int pieces) throws SQLException  {

		DataBaseConnection.openConnection();
		CallableStatement stmt = DataBaseConnection.prepareCall("CALL add_bass(?,?,?,?)");
		try {
			stmt.setString(1, brand);
			stmt.setInt(2, model);
			stmt.setDouble(3, price);
			stmt.setString(4, kind);
			stmt.setInt(5, pieces);
			DataBaseConnection.executeCallableStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			DataBaseConnection.closeConnection();
			return false;
		}

		DataBaseConnection.closeConnection();
		return true;
	}

	public int[] getUserGenderStat(int param) {
		DataBaseConnection.openConnection();
		ResultSet rs = DataBaseConnection.sqlStatement("CALL get_gender_stats");
		int male = 0;
		int female = 0;
		try {
			rs.next();
			if (rs.getString(1).equals("M"))
				male = rs.getInt(param);
			else
				female = rs.getInt(param);
			rs.next();
			if (rs.getString(1).equals("M"))
				male = rs.getInt(param);
			else
				female = rs.getInt(param);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DataBaseConnection.closeConnection();
		int[] ret = { male, female };

		return ret;
	}

	public HashMap<String, int[]> getProductStats(String group) {
		DataBaseConnection.openConnection();

		ResultSet rs = null;

		if (group.equals("type")) {
			rs = DataBaseConnection.sqlStatement(("CALL product_by_type"));
		}

		else if (group.equals("brand")) {
			rs = DataBaseConnection.sqlStatement(("CALL product_by_brand"));
		}

		HashMap<String, int[]> result = new HashMap<>();

		try {

			while (rs.next()) {
				String attribute = rs.getString(1);
				int bought = rs.getInt(2);
				int view = rs.getInt(3);
				int count = rs.getInt(4);

				int[] stats = { bought, view, count };
				result.put(attribute, stats);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DataBaseConnection.closeConnection();
		return result;
	}

	public List<ReviewPojo> getReviewByUser(int user) throws SQLException {

		DataBaseConnection.openConnection();
		CallableStatement stmt = DataBaseConnection.prepareCall("SELECT * FROM review WHERE user = ?");
		try {
			stmt.setInt(1, user);

			ResultSet rs = DataBaseConnection.executeCallableStatement();

			ArrayList<ReviewPojo> ret = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt("id");
				int model = rs.getInt("model");
				int userId = rs.getInt("user");
				int rating = rs.getInt("rating");
				String review = rs.getString("review");

				ret.add(new ReviewPojo(id, model, userId, rating, review));

			}

			DataBaseConnection.closeConnection();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<SalePojo> getSaleByUser(int userId) throws SQLException {
		DataBaseConnection.openConnection();
		CallableStatement stmt = DataBaseConnection.prepareCall("SELECT * FROM sales WHERE user = ?");
		try {
			stmt.setInt(1, userId);

			ResultSet rs = DataBaseConnection.executeCallableStatement();

			ArrayList<SalePojo> ret = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt("id");
				int user = rs.getInt("user");
				int item = rs.getInt("item");
				int amount = rs.getInt("amount");

				ret.add(new SalePojo(id, user, item, amount));
			}

			DataBaseConnection.closeConnection();
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
	}

}
