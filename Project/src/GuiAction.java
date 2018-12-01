import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiAction {

	public List<ProductPojo> getProducts() {

		ArrayList<ProductPojo> ret = new ArrayList<>();
		
		DataBaseConnection db = new DataBaseConnection();
		db.openConnection();
		
		
		ResultSet rs = db.sqlStatement("SELECT * FROM product");
		
		
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
		
		db.closeConnection();
		return ret;
		
	}

}
