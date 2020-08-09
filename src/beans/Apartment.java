package beans;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import enums.ApartmentType;

public class Apartment {
	
	private ApartmentType apartmentType;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private List<Date> allDates;
	private List<Date> availableDates;
	private Host host;
	private List<Comment> comments;
	private List<String> pictures;
	private double pricePerNight;
	private LocalTime checkInTime;
	private LocalTime checkOutTime;
	private boolean status;
	private List<Amenities> amenities;
	private List<Reservation> reservations;
	
	
	public Apartment(ApartmentType apartmentType, int numberOfRooms, int numberOfGuests, Location location,
			List<Date> allDates, List<Date> availableDates, Host host, List<Comment> comments, List<String> pictures,
			double pricePerNight, LocalTime checkInTime, LocalTime checkOutTime, boolean status,
			List<Amenities> amenities, List<Reservation> reservations) {
		this.apartmentType = apartmentType;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.allDates = allDates;
		this.availableDates = availableDates;
		this.host = host;
		this.comments = comments;
		this.pictures = pictures;
		this.pricePerNight = pricePerNight;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.status = status;
		this.amenities = amenities;
		this.reservations = reservations;
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


	public List<Date> getAvailableDates() {
		return availableDates;
	}


	public void setAvailableDates(List<Date> availableDates) {
		this.availableDates = availableDates;
	}


	public Host getHost() {
		return host;
	}


	public void setHost(Host host) {
		this.host = host;
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


	public LocalTime getCheckInTime() {
		return checkInTime;
	}


	public void setCheckInTime(LocalTime checkInTime) {
		this.checkInTime = checkInTime;
	}


	public LocalTime getCheckOutTime() {
		return checkOutTime;
	}


	public void setCheckOutTime(LocalTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
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
	
	
	
}
