package beans;

import java.util.Date;

import enums.ReservationStatus;

public class Reservation {

	private Apartment reservedApartment;
	private Date checkInDate;
	private Date endDate;
	private int numberOfNights; 
	private double fullPrice; 
	private String reservationMessage;
	private User guest;
	private ReservationStatus reservationStatus;
	private String startDateString;
	private String endDateString;
	
	public Reservation() {}
	
	public Reservation(Apartment apartment, Date checkInDate, Date endDate,int numberOfNights, double fullPrice,
			String reservationMessage, User guest, ReservationStatus reservationStatus , String startString , String endString) {
		super();
		this.reservedApartment = apartment;
		this.checkInDate = checkInDate;
		this.endDate = endDate;
		this.numberOfNights = numberOfNights;
		this.fullPrice = fullPrice;
		this.reservationMessage = reservationMessage;
		this.guest = guest;
		this.reservationStatus = reservationStatus;
		this.startDateString = startString;
		this.endDateString = endString;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Apartment getReservedApartment() {
		return reservedApartment;
	}

	public void setReservedApartment(Apartment reservedApartment) {
		this.reservedApartment = reservedApartment;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public int getNumberOfNights() {
		return numberOfNights;
	}

	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	public double getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(double fullPrice) {
		this.fullPrice = fullPrice;
	}

	public String getReservationMessage() {
		return reservationMessage;
	}

	public void setReservationMessage(String reservationMessage) {
		this.reservationMessage = reservationMessage;
	}

	public User getGuest() {
		return guest;
	}

	public void setGuest(User guest) {
		this.guest = guest;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	@Override
	public String toString() {
		return "Reservation [reservedApartment=" + reservedApartment + ", checkInDate=" + checkInDate + ", endDate="
				+ endDate + ", numberOfNights=" + numberOfNights + ", fullPrice=" + fullPrice + ", reservationMessage="
				+ reservationMessage + ", guest=" + guest + ", reservationStatus=" + reservationStatus
				+ ", startDateString=" + startDateString + ", endDateString=" + endDateString + "]";
	}
	
}
