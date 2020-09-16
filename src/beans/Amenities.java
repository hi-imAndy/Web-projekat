package beans;

public class Amenities {
	private String deleted;
	private int id;
	private String name;
	
	public Amenities() {}
	
	public Amenities(int id, String name) {
		this.deleted = "NO";
		this.id = id;
		this.name = name;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Amenities [deleted=" + deleted + ", id=" + id + ", name=" + name + "]";
	}
	
}
