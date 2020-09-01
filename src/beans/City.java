package beans;

public class City {

	private String postCode;
	private String name;
	
	public City() {}
	
	public City(String postCode, String name) {
		this.postCode = postCode;
		this.name = name;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
