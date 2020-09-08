package beans;

import java.util.Date;
import enums.ReservationStatus;

public class Reservation {

	private String id;
	private Apartment reservedApartment;
	private Date checkInDate;
	private int numberOfNights; 
	private double fullPrice; 
	private String reservationMessage;
	private User guest;
	private ReservationStatus reservationStatus;
	
	public Reservation() {}
	
	public Reservation(String id, Apartment reservedApartment, Date checkInDate, int numberOfNights, double fullPrice,
			String reservationMessage, User guest, ReservationStatus reservationStatus) {
		this.id = id;
		this.reservedApartment = reservedApartment;
		this.checkInDate = checkInDate;
		this.numberOfNights = numberOfNights;
		this.fullPrice = fullPrice;
		this.reservationMessage = reservationMessage;
		this.guest = guest;
		this.reservationStatus = reservationStatus;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	
	
}
