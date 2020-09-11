package beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import enums.ApartmentStatus;
import enums.ApartmentType;

public class Apartment {
	
	private String id;
	private ApartmentType apartmentType;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private List<Date> allDates;
	private ArrayList<Date> availableDates;
	private User user;
	private List<Comment> comments;
	private List<String> pictures;
	private double pricePerNight;
	private String checkInTime;
	private String checkOutTime;
	private ApartmentStatus status;
	private List<Amenities> amenities;
	private List<Reservation> reservations;
	private ArrayList<String> availableDatesString;
	
	public Apartment() {}
	
	public Apartment(String id, ApartmentType apartmentType, int numberOfRooms, int numberOfGuests, Location location,
			List<Date> allDates,ArrayList<Date> availableDates, User user, List<String> pictures,
			double pricePerNight, String checkInTime, String checkOutTime, ApartmentStatus status,
			List<Amenities> amenities) {
		this.id = id;
		this.apartmentType = apartmentType;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.allDates = allDates;
		this.availableDates = availableDates;
		this.user = user;
		this.comments = new ArrayList<Comment>();
		this.pictures = pictures;
		this.pricePerNight = pricePerNight;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.status = status;
		this.amenities = amenities;
		this.reservations = new ArrayList<Reservation>();

		
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ApartmentType getApartmentType() {
		return apartmentType;
	}


	public void setApartmentType(ApartmentType apartmentType) {
		this.apartmentType = apartmentType;
	}


	public int getNumberOfRooms() {
		return numberOfRooms;
	}


	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}


	public int getNumberOfGuests() {
		return numberOfGuests;
	}


	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public List<Date> getAllDates() {
		return allDates;
	}


	public void setAllDates(List<Date> allDates) {
		this.allDates = allDates;
	}


	public ArrayList<Date> getAvailableDates() {
		return availableDates;
	}


	public void setAvailableDates(ArrayList<Date> availableDates) {
		this.availableDates = availableDates;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<Comment> getComments() {
		return comments;
	}


	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	public List<String> getPictures() {
		return pictures;
	}


	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}


	public double getPricePerNight() {
		return pricePerNight;
	}


	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}


	public String getCheckInTime() {
		return checkInTime;
	}


	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}


	public String getCheckOutTime() {
		return checkOutTime;
	}


	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}


	public ApartmentStatus getStatus() {
		return status;
	}


	public void setStatus(ApartmentStatus status) {
		this.status = status;
	}


	public List<Amenities> getAmenities() {
		return amenities;
	}


	public void setAmenities(List<Amenities> amenities) {
		this.amenities = amenities;
	}


	public List<Reservation> getReservations() {
		return reservations;
	}


	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	
	
	public ArrayList<String> getAvailableDatesString() {
		return availableDatesString;
	}

	public void setAvailableDatesString(ArrayList<String> availableDatesString) {
		this.availableDatesString = availableDatesString;
	}

	@Override
	public String toString() {
		return "Apartment [id=" + id + ", apartmentType=" + apartmentType + ", numberOfRooms=" + numberOfRooms
				+ ", numberOfGuests=" + numberOfGuests + ", location=" + location + ", allDates=" + allDates
				+ ", availableDates=" + availableDates + ", user=" + user + ", comments=" + comments + ", pictures="
				+ pictures + ", pricePerNight=" + pricePerNight + ", checkInTime=" + checkInTime + ", checkOutTime="
				+ checkOutTime + ", status=" + status + ", amenities=" + amenities + ", reservations=" + reservations
				+ "]";
	}
	
	
	
}
