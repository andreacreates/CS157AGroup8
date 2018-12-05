
public class ReviewPojo {
	int id, model, user, rating;
	String review;
	public ReviewPojo(int id, int model, int user, int rating, String review) {
		super();
		this.id = id;
		this.model = model;
		this.user = user;
		this.rating = rating;
		this.review = review;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	
	public String toString() {
		String header = "User ID: " + user + ", Model: " + model + ", Rating: " + rating + "\n";
		return header + review; 
	}
	
	
}
