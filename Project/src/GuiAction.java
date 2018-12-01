import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public String getProductDetails(ProductPojo p) {

		String type = p.getType().toLowerCase();
		int model = p.getModel();

		DataBaseConnection.openConnection();

		PreparedStatement stmt = DataBaseConnection
				.createPreparedStatement("SELECT * FROM " + type + " WHERE model = ?");
		try {
			stmt.setInt(1, model);
			ResultSet res = stmt.executeQuery();
			res.next();

			if (type.equals("guitar")) {
				String ret = "Price: " + res.getFloat("price") + "\n" + "Type: " + res.getString("type") + "\n"
						+ "Size: " + res.getString("size") + "\n";

				DataBaseConnection.closeConnection();
				return ret;
			}

			else if (type.equals("bass")) {
				String ret = "Price: " + res.getFloat("price") + "\n" + "Type: " + res.getString("type") + "\n";

				DataBaseConnection.closeConnection();
				return ret;
			}

			else {

				String ret = "Price: " + res.getFloat("price") + "\n" + "Type: " + res.getString("type") + "\n"
						+ "Pieces: " + res.getString("pieces") + "\n";

				DataBaseConnection.closeConnection();
				return ret;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Error";
	}

}
