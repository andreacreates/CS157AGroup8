
public class ProductPojo {
	
	String brand;
	int model;
	String type;
	int bought;
	int views;
	
	
	public ProductPojo(String brand, int model, String type, int bought, int views) {
		super();
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.bought = bought;
		this.views = views;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getBought() {
		return bought;
	}
	public void setBought(int bought) {
		this.bought = bought;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	
	public String toString() {
		return model + brand + type + views + bought;
	}
	
	

}
