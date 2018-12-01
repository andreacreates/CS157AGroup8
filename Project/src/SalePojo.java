
public class SalePojo {
	int id;
	int user;
	int item;
	int amount;

	public SalePojo(int id, int user, int item, int amount) {
		super();
		this.id = id;
		this.user = user;
		this.item = item;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
