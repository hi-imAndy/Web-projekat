package beans;

import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Role;

public class Guest extends User {

	private List<Apartment> rentedApartments;
	private List<Reservation> reservations;
	
	public Guest(String username, String password, String firstName, String lastName, Gender gender, Role role) {
		super(username, password, firstName, lastName, gender, role);
		this.rentedApartments = new ArrayList<Apartment>();
		this.reservations = new ArrayList<Reservation>();
	}

	public Guest(String username, String password, String firstName, String lastName, Gender gender, Role role,
			List<Apartment> rentedApartments, List<Reservation> reservations) {
		super(username, password, firstName, lastName, gender, role);
		this.rentedApartments = rentedApartments;
		this.reservations = reservations;
	}
/*
	public ArrayList<Apartment> getRentedApartments() {
		return rentedApartments;
	}

	public void setRentedApartments(List<Apartment> rentedApartments) {
		this.rentedApartments = rentedApartments;
	}

	public ArrayList<Apartment> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	*/
	
}
