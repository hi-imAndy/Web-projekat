package beans;

public class ReservationInfo {
	private User user;
	private Apartment apartment;
	private String startDate;
	private String endDate;
	private String reservationMessage;
	
	public ReservationInfo(User user, Apartment apartment, String startDate, String endDate,
			String reservationMessage) {
		super();
		this.user = user;
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservationMessage = reservationMessage;
	}
	
	public ReservationInfo() {
		
	}
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReservationMessage() {
		return reservationMessage;
	}

	public void setReservationMessage(String reservationMessage) {
		this.reservationMessage = reservationMessage;
	}
	
	
	
}
