package beans;

import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Role;

public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Role role;
	
	private List<Apartment> apartmentsForRent;
	private List<Apartment> rentedApartments;
	private List<Reservation> reservations;
	
	public User() {
		
	}

	public User(String username, String password, String firstName, String lastName, Gender gender, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.role = role;
		this.apartmentsForRent = new ArrayList<Apartment>();
		this.rentedApartments = new ArrayList<Apartment>();
		this.reservations = new ArrayList<Reservation>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Apartment> getApartmentsForRent() {
		return apartmentsForRent;
	}

	public void setApartmentsForRent(List<Apartment> apartmentsForRent) {
		this.apartmentsForRent = apartmentsForRent;
	}

	public List<Apartment> getRentedApartments() {
		return rentedApartments;
	}

	public void setRentedApartments(List<Apartment> rentedApartments) {
		this.rentedApartments = rentedApartments;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", gender=" + gender + ", role=" + role + ", apartmentsForRent=" + apartmentsForRent
				+ ", rentedApartments=" + rentedApartments + ", reservations=" + reservations + "]";
	}
	
}
