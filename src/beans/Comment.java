package beans;

public class Comment {

	private Guest author;
	private Apartment apartment;
	private String content;
	private int rating;
	
	public Comment() {}
	
	public Comment(Guest author, Apartment apartment, String content, int rating) {
		this.author = author;
		this.apartment = apartment;
		this.content = content;
		this.rating = rating;
	}

	public Guest getAuthor() {
		return author;
	}

	public void setAuthor(Guest author) {
		this.author = author;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
