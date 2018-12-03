
public class UserPojo {

	int id;
	int age;
	String gender;
	float browseTime;


	public UserPojo (int id, int age, String gender, float browseTime) {
		super();
		this.id = id;
		this.age = age;
		this.gender = gender;
		this.browseTime = browseTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setType(String gender) {
		this.gender = gender;
	}

	public float getBrowseTime() {
		return browseTime;
	}

	public void setBrowseTime(int browseTime) {
		this.browseTime = browseTime;
	}

    public String toString() {
		return id + age + gender + browseTime;
	}

}
