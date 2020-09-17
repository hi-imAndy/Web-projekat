package dao;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Address;
import beans.Amenities;
import beans.Apartment;
import beans.City;
import beans.Comment;
import beans.DateSubstitute;
import beans.FilterInfoHost;
import beans.Location;
import beans.Reservation;
import beans.ReservationInfo;
import beans.User;
import enums.ApartmentStatus;
import enums.ReservationStatus;

public class ApartmentDAO {

	private Map<String, Apartment> apartments = new HashMap<>();
	private String path;
	
	public ApartmentDAO() {
		
	}
	
	public ApartmentDAO(String contextPath) {
		this.path = contextPath;
		loadApartments();
	}
	
	public boolean addNewApartment(Apartment ap) {
		if(apartments.containsKey(ap.getId())) {
			return false;
		}
		apartments.put(ap.getId(), ap);
		saveApartment(ap);
		return true;
	}
	
	public void acceptReservation(Reservation reservation) {
		for(Apartment ap : apartments.values()) {
			for(Reservation r : ap.getReservations()) {
				if(r.getReservedApartment() == null) {
					break;
				}
				if(r.getReservedApartment().getId().equals(reservation.getReservedApartment().getId()) && r.getGuest().getUsername().equals(reservation.getGuest().getUsername())){
					r.setReservationStatus(ReservationStatus.ACCEPTED);
					break;
				}
			}
		}
		saveAllApartments();
	}
	
	public void rejectReservation(Reservation reservation) {
		for(Apartment ap : apartments.values()) {
			for(Reservation r : ap.getReservations()) {
				if(r.getReservedApartment() == null) {
					break;
				}
				if(r.getReservedApartment().getId().equals(reservation.getReservedApartment().getId()) && r.getGuest().getUsername().equals(reservation.getGuest().getUsername())){
					r.setReservationStatus(ReservationStatus.REJECTED);
					break;
				}
			}
		}
		saveAllApartments();
	}
	
	public void finishReservation(Reservation reservation) {
		for(Apartment ap : apartments.values()) {
			for(Reservation r : ap.getReservations()) {
				if(r.getReservedApartment() == null) {
					break;
				}
				if(r.getReservedApartment().getId().equals(reservation.getReservedApartment().getId()) && r.getGuest().getUsername().equals(reservation.getGuest().getUsername())){
					r.setReservationStatus(ReservationStatus.FINISHED);
					break;
				}
			}
		}
		saveAllApartments();
	}
	
	private void saveApartment(Apartment ap) {
		
		apartments.put(ap.getId(), ap);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String apAsString = objectMapper.writeValueAsString(ap);
			FileWriter fileWriter = new FileWriter(path + "files\\apartments.json", true);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.print(apAsString);
		    printWriter.print("\n");
			printWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteApartment(String id) {
		apartments.get(id).setDeleted("YES");
		saveAllApartments();
	}
	
	public void editApartment(Apartment ap) {
		apartments.replace(ap.getId(), ap);
		saveAllApartments();
	}
	
	private void loadApartments() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path + "/files/apartments.json"));
			String line = br.readLine();
			while(line != null) {
				Apartment newApartment = objectMapper.readValue(line, Apartment.class);
				apartments.put(newApartment.getId(), newApartment);
				line = br.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAmenitieInApartments(Amenities amenitie) {
		for(Apartment ap : apartments.values()) {
			for(int i = 0; i < ap.getAmenities().size(); i++) {
				if(ap.getAmenities().get(i).getId() == amenitie.getId() && ap.getAmenities().get(i).getName().equals(amenitie.getName())) {
					ap.getAmenities().remove(i);
				}
			}
		}
		saveAllApartments();
	}
	
	private void saveAllApartments() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			FileWriter fileWriter = new FileWriter(path + "files\\apartments.json", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			for(Apartment ap : apartments.values()) {
				String apAsString = objectMapper.writeValueAsString(ap);
				printWriter.print(apAsString);
			    printWriter.print("\n");
			}
			printWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
public Collection<Apartment> filterHost(FilterInfoHost filterInfo){
		
		Set<Apartment> apByType = new HashSet<Apartment>();
		Set<Apartment> apByStatus = new HashSet<Apartment>();
		Set<Apartment> apByAmenities = new HashSet<Apartment>();
		
		for(Apartment ap : apartments.values()) {
			if(filterInfo.getType() != null)
				if(ap.getApartmentType().equals(filterInfo.getType()))
					apByType.add(ap);
			if(filterInfo.getStatus() != null)
				if(ap.getStatus().equals(filterInfo.getStatus()))
					apByStatus.add(ap);
			if(filterInfo.getAmenities() != null) {
				int i = 0;
				for(Amenities a : filterInfo.getAmenities()) {
					for(int j =  0; j < ap.getAmenities().size(); j++) {
						if(ap.getAmenities().get(j).getId() == a.getId())
							i++;
					}
						
				}
				if(i == filterInfo.getAmenities().size()) {
					apByAmenities.add(ap);
				}
			}
		}
		
		Set<Apartment> ret_apartments;
		
		if(filterInfo.getType() != null) {
			ret_apartments = new HashSet<Apartment>(apByType);
			if(filterInfo.getStatus() != null)
				ret_apartments.retainAll(apByStatus);
			if(filterInfo.getAmenities() != null)
				ret_apartments.retainAll(apByAmenities);
		}else {
			if(filterInfo.getStatus() != null) {
				ret_apartments = new HashSet<Apartment>(apByStatus);
				if(filterInfo.getAmenities() != null)
					ret_apartments.retainAll(apByAmenities);
			}else {
				ret_apartments = new HashSet<Apartment>(apByAmenities);
			}
		}
		
		return ret_apartments;
	}
	
	public Collection<Apartment> getApartmentsByUsername(String username){
		ArrayList<Apartment> ret_list = new ArrayList<Apartment>();
		for(Apartment ap : apartments.values()) {
			if(ap.getUser().getUsername().equals(username)) {
				ret_list.add(ap);
			}
		}
		return ret_list;
	}
	

	public Collection<Apartment> getAllApartments(){
	
		//OVA 2 FORA INICIJALIZUJU LISTE KOJE SU INACE NULL KADA SE SREDE JSON FAJLOVI OBRISATI ISTO VAZI I ZA USERA
		for(Apartment ap: apartments.values()) {
			ap.setAvailableDatesString(new ArrayList<String>());
		
		
			for(Date date : ap.getAvailableDates()) {
				ap.getAvailableDatesString().add(date.getYear()+ 1900 +"-"+date.getMonth()+"-"+date.getDate());
			}	
			

		}
		
		for(Apartment ap: apartments.values()) {
			if(ap.getComments() == null)
				ap.setComments(new ArrayList<Comment> ());		

		}
			for(Apartment ap : apartments.values()) {
				if(ap.getReservations() == null)
					ap.setReservations(new ArrayList<Reservation>());
			}
			
		return apartments.values();
	}
	
	public Apartment getApartmentById(String id){

		return apartments.get(id);
	}
	
	//POMOCNA FUNKCIJA ZA POREDJENJE DATUMA
	private boolean compareDates(Date startDate ,Date endDate, ArrayList<Date> availableDates) {
		ArrayList<DateSubstitute> availableDatesSub = new ArrayList<DateSubstitute>();
		ArrayList<DateSubstitute> startToEnd = new ArrayList<DateSubstitute>();
		
		for(int i = 0 ; i < availableDates.size() ; i++) {
			availableDatesSub.add(new DateSubstitute(availableDates.get(i).getDate(), availableDates.get(i).getMonth(), availableDates.get(i).getYear()+1900));
			//System.out.println(availableDatesSub.get(i).getDay() + "." + availableDatesSub.get(i).getMonth() + "." + availableDatesSub.get(i).getYear() );
		}
		
		int differenceDay = endDate.getDate() - startDate.getDate();
		int differenceMonth = endDate.getMonth() - startDate.getMonth();
		int differenceYear = endDate.getYear() - startDate.getYear();
		
		//ISTI MESEC ISTA GODINA RAZLICIT DAN
		if(differenceDay > 0 && differenceMonth == 0 && differenceYear == 0) {
			for(int i = 0 ; i <= differenceDay ; i++) {
				startToEnd.add(new DateSubstitute(startDate.getDate() + i, startDate.getMonth(), startDate.getYear()));
				//System.out.println(startToEnd.get(i).getDay() + "." + startToEnd.get(i).getMonth() + "." + startToEnd.get(i).getYear() );
			}
		}
		//RAZLICIT MESEC ISTA GODINA 
		else if(differenceMonth > 0 && differenceYear == 0) {
			if(startDate.getMonth() == 1 || startDate.getMonth() == 3 || startDate.getMonth() == 5 || startDate.getMonth() == 7 || startDate.getMonth() == 8 || startDate.getMonth() == 10 || startDate.getMonth() == 12) {
			for(int i = 0 ; i <= 31 ; i++) {
				startToEnd.add(new DateSubstitute(startDate.getDate()+i, startDate.getMonth(), startDate.getYear()));
				if(startDate.getDate()+i == 31)
					break;
			}
			for(int i = 0 ; i <= endDate.getDate() ; i++) {
				startToEnd.add(new DateSubstitute(0+i, endDate.getMonth(), startDate.getYear()));
			}
		}
			else if(startDate.getMonth() == 4 || startDate.getMonth() == 6 || startDate.getMonth() == 9 || startDate.getMonth() == 11 ) {
				for(int i = 0 ; i <= 30 ; i++) {
					startToEnd.add(new DateSubstitute(startDate.getDate()+i, startDate.getMonth(), startDate.getYear()));
					if(startDate.getDate()+i == 31)
						break;
				}
				for(int i = 0 ; i <= endDate.getDate() ; i++) {
					startToEnd.add(new DateSubstitute(0+i, endDate.getMonth(), startDate.getYear()));
				}
			}
		}
		
		for(DateSubstitute ds : startToEnd) {
			for(DateSubstitute dsAD : startToEnd) {
				if(!checkIfContains(availableDatesSub, dsAD))
					return false;
			}
		}

		return true;
	}
	
	
	
	//POMOCNA FUNKCIJA 
	private boolean checkIfContains (ArrayList<DateSubstitute> dsList, DateSubstitute ds ) {
		for(DateSubstitute dsL : dsList) {
			if(dsL.getDay() == ds.getDay() && dsL.getMonth() == ds.getMonth() && dsL.getYear() == ds.getYear()) {
				return true;
			}
		}
		return false;	
	}
	
	public Collection<Apartment> filterApartments(String location,int numberOfGuests,double pricePerNightMin,double pricePerNightMax , String startDateString,String endDateString , int numberOfRoomsMin , int numberOfRoomsMax){
		Map<String, Apartment> returnValue = new HashMap<>();
		
		if(location != null) {
			if(location.equalsIgnoreCase("All"))
				location = null;
		}
		
		if(pricePerNightMin != -1 && pricePerNightMax == -1) {
			pricePerNightMax = 10000000;
		}
		else if (pricePerNightMax != -1 && pricePerNightMin == -1) {
			pricePerNightMin = 1;
		}
		if(numberOfRoomsMin != -1 && numberOfRoomsMax == -1) {
			numberOfRoomsMax = 10000000;
		}
		else if (numberOfRoomsMax != -1 && numberOfRoomsMin == -1) {
			numberOfRoomsMin = 1;
		}
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = new Date(Integer.parseInt(startDateString.split("-")[0]),Integer.parseInt(startDateString.split("-")[1]),Integer.parseInt(startDateString.split("-")[2]));
			endDate = new Date(Integer.parseInt(endDateString.split("-")[0]),Integer.parseInt(endDateString.split("-")[1]),Integer.parseInt(endDateString.split("-")[2]));
		}
		catch (Exception e) {
			startDate = null;
			endDate = null;
		}
		
		//LOKACIJA 
			if(location != null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate == null && endDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location))
						returnValue.put(ap.getId(),ap);
				}
			}	
		
		//LOKACIJA + SOBE
			if(location != null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//DATUM 
			if(location == null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					try {
						 {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}	
		
		//DATUM + SOBE
			if(location == null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
			
		//LOKACIJA + CENA
			if(location != null && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//LOKACIJA + CENA + SOBE
			if(location != null && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}		
			
		//LOKACIJA + DATUM
			if(location != null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location))
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
		
			//LOKACIJA + DATUM + SOBE
			if(location != null && numberOfGuests == 0 && pricePerNightMin==-1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}
			
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU + SOBE
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU + DATUM
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
		
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU + DATUM + SOBE
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}		
			
		//BROJ GOSTIJU + LOKACIJA
			if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && endDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests )
						returnValue.put(ap.getId(),ap);
				}
			}
		
		//BROJ GOSTIJU + LOKACIJA + SOBE
			if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + LOKACIJA + DATUM
		if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests )
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}
		
		//BROJ GOSTIJU + LOKACIJA + DATUM + SOBE
		if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}
		
		// LOKACIJA + CENA + DATUM
		if(location != null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
			for(Apartment ap : apartments.values()) {
				if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
					try {
						if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
							returnValue.put(ap.getId(),ap);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
			}
		}
		
		
		// LOKACIJA + GOSTI + DATUM + SOBE
		if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
			for(Apartment ap : apartments.values()) {
				if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax )
					try {
						if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
							returnValue.put(ap.getId(),ap);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
			}
		}
		
		//BROJ GOSTIJU
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && endDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests)
						returnValue.put(ap.getId(),ap);
				}
			}	
		
		//BROJ GOSTIJU + SOBE
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + DATUM
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
			
		//BROJ GOSTIJU + DATUM + SOBE
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}
			
		// BROJ GOSTIJU + CENA NOCENJA	
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1 ) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
		
		// BROJ GOSTIJU + CENA NOCENJA	+ SOBE
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + CENA NOCENJA + DATUM
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1 ) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)

						try {
							for(Date date : ap.getAvailableDates()) {
								if(date.getDate() == startDate.getDate() && date.getMonth() == startDate.getMonth() && date.getYear() == startDate.getYear()) {
									returnValue.put(ap.getId(),ap);
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
		
		//BROJ GOSTIJU + CENA NOCENJA + DATUM + SOBE
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)

						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
			
		//CENA PO NOCENJU
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}
			
		//CENA PO NOCENJU + SOBE
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}
			
		//SOBE
			if(location == null  && numberOfGuests == 0 && pricePerNightMin == -1 && startDate == null && endDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(  ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}
			
		//CENA PO NOCENJU + DATUM
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
			
			//CENA PO NOCENJU + DATUM
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}
			
			
			//CENA PO NOCENJU + DATUM + SOBE
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && endDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						try {
							if(compareDates(startDate, endDate, (ArrayList<Date>) ap.getAvailableDates())) {
								returnValue.put(ap.getId(),ap);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
				}
			}	
			
			if(location == null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate == null && endDate == null && numberOfRoomsMin == -1 &&  numberOfRoomsMax == -1 && pricePerNightMin == -1 )
				returnValue = apartments;
		return returnValue.values();
	}
	
	//POMOCNA FUNKCIJA ZA BRISANJE DATUMA
		private ArrayList<Date> deleteDates(Date startDate ,int numberOfNights, ArrayList<Date> availableDates) {
			ArrayList<DateSubstitute> availableDatesSub = new ArrayList<DateSubstitute>();
			ArrayList<DateSubstitute> startToEnd = new ArrayList<DateSubstitute>();
			
			for(int i = 0 ; i < availableDates.size() ; i++) {
				availableDatesSub.add(new DateSubstitute(availableDates.get(i).getDate(), availableDates.get(i).getMonth(), availableDates.get(i).getYear()+1900));
			}
			

			
			int date = startDate.getDate();
			int month = startDate.getMonth();
			int year = startDate.getYear();
			
		for(int i = 0 ; i < numberOfNights ; i++) {
			
			if(startDate.getMonth() == 1 || startDate.getMonth() == 3 || startDate.getMonth() == 5 || startDate.getMonth() == 7 || startDate.getMonth() == 8 || startDate.getMonth() == 10 || startDate.getMonth() == 12) {
				if(date == 31 && month != 12) {
					date = 1;
					month++;
				}
				else if(date == 31 && month == 12) {
					date = 1;
					month = 1;
					year++;
				}
			}
			else if(startDate.getMonth() == 4 || startDate.getMonth() == 6 || startDate.getMonth() == 9 || startDate.getMonth() == 11) {
				if(date == 30) {
					date = 1;
					month++;
				}
			}
			else if(startDate.getMonth() == 2 ) {
				if(date == 29) {
					date = 1;
					month++;
				}
			}
			startToEnd.add(new DateSubstitute(date,month, year));
			date++;
		}
			
			ArrayList<DateSubstitute> retValT = new ArrayList<DateSubstitute>();
			
			for(DateSubstitute ds : availableDatesSub) {
					if(!checkIfContains(startToEnd, ds))
						retValT.add(ds);
			}

			ArrayList<Date> retVal = new ArrayList<Date>();
			for(DateSubstitute ds : retValT) {
				retVal.add(new Date(ds.getYear() - 1900,ds.getMonth(),ds.getDay()));
			}
			return  retVal;
		}

		//POVRATNA VREDNOST JE UVEK DOBRA
	public String bookApartment(ReservationInfo reservationInfo) {
		
		
		User user = reservationInfo.getUser();
		String startDateString = reservationInfo.getStartDate();
		
		String reservationMessage = reservationInfo.getReservationMessage();
		int numberOfNights = reservationInfo.getNumberOfNights();

		Date startDate = new Date(Integer.parseInt(startDateString.split("-")[0]),Integer.parseInt(startDateString.split("-")[1]),Integer.parseInt(startDateString.split("-")[2]));
		int dayEndDate= startDate.getDate();
		int monthEndDate = startDate.getMonth();
		int yearEndDAte = startDate.getYear();
		

		
		for(int i = 0 ; i < numberOfNights-1 ; i++) {
			if(startDate.getMonth() == 1 || startDate.getMonth() == 3 || startDate.getMonth() == 5 || startDate.getMonth() == 7 || startDate.getMonth() == 8 || startDate.getMonth() == 10 || startDate.getMonth() == 12) {
				if(dayEndDate == 31 && monthEndDate != 12) {
					dayEndDate = 1;
					monthEndDate++; 
				}
				else if(dayEndDate == 31 && monthEndDate == 12){
					dayEndDate = 1;
					monthEndDate = 1; 
					yearEndDAte ++;
				}
			}
			else if(startDate.getMonth() == 4 || startDate.getMonth() == 6 || startDate.getMonth() == 9 || startDate.getMonth() == 11 ) {
				if(dayEndDate == 30 ) {
					dayEndDate = 1;
					monthEndDate++; 
				}
			}
			else if(startDate.getMonth() == 2 ) {
				if(dayEndDate == 29 ) {
					dayEndDate = 1;
					monthEndDate++; 
				}
			}
			dayEndDate += i;
		}
		
		Date endDate = new Date(yearEndDAte,monthEndDate,dayEndDate);	
		
		if(checkIfDatesAreAvailable(reservationInfo.getApartment().getAvailableDatesString(), startDate, endDate , numberOfNights)){
		
			String endDateString =new String(endDate.getYear() +"-"+endDate.getMonth()+"-"+endDate.getDate());
			
			Reservation reservation = new Reservation(reservationInfo.getApartment(), startDate, endDate , numberOfNights, reservationInfo.getApartment().getPricePerNight()*numberOfNights, reservationMessage, user , ReservationStatus.CREATED,reservationInfo.getStartDate(),new String(endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDate()));
			
			//OVU LINIJU TREBA OBRISATI KADA SE SREDE OBJEKTI
		/*//PRAVI LISTE REZERVACIJA
			for(Apartment ap : apartments.values()) {
				ap.setReservations(new ArrayList<Reservation>());
			}
		*/		
			
		/*
			for(Apartment ap: apartments.values()) {
				ap.setAvailableDatesString(new ArrayList<String>());
			
			
				for(Date date : ap.getAvailableDates()) {
					ap.getAvailableDatesString().add(date.getYear()+ 1900 +"-"+date.getMonth()+"-"+date.getDate());
				}	
			}
		*/	
			apartments.get(reservationInfo.getApartment().getId()).getReservations().add(reservation);
			
			ArrayList<Date> availableDates = (ArrayList<Date>) apartments.get(reservationInfo.getApartment().getId()).getAvailableDates();
			apartments.get(reservationInfo.getApartment().getId()).setAvailableDates(deleteDates(startDate, numberOfNights, availableDates));
			
			apartments.get(reservationInfo.getApartment().getId()).setAvailableDatesString(new ArrayList<String>());
			
			for(Date date : apartments.get(reservationInfo.getApartment().getId()).getAvailableDates()) {
				apartments.get(reservationInfo.getApartment().getId()).getAvailableDatesString().add(date.getYear()+1900+"-"+date.getMonth()+"-"+date.getDate());
			}
			
			saveAllApartments();

			return "200 OK";
		}

		return "400 BAD REQUEST";
		
	}
	
	//PROVERAVA DA LI SU DATUMI DOSTUPNI -FUNKCIJA PROVERENA RADI!!!
	public boolean checkIfDatesAreAvailable(ArrayList<String> availableDatesString , Date startDate , Date endDate , int numberOfNights) {
		ArrayList<DateSubstitute> availableDatesSub = new ArrayList<DateSubstitute>();

		
			for(String s : availableDatesString) {
				availableDatesSub.add(new DateSubstitute(Integer.parseInt(s.split("-")[2]), Integer.parseInt(s.split("-")[1]), Integer.parseInt(s.split("-")[0])));
			}
		
			int differenceDay = numberOfNights;
			int differenceMonth = endDate.getMonth() - startDate.getMonth();
			int differenceYear = endDate.getYear() - startDate.getYear();
			

			
			ArrayList<DateSubstitute> startToEnd = new ArrayList<DateSubstitute>();
				
			
				int date = startDate.getDate();
				int month = startDate.getMonth();
				int year = startDate.getYear();
				
			for(int i = 0 ; i < numberOfNights ; i++) {
				
				if(startDate.getMonth() == 1 || startDate.getMonth() == 3 || startDate.getMonth() == 5 || startDate.getMonth() == 7 || startDate.getMonth() == 8 || startDate.getMonth() == 10 || startDate.getMonth() == 12) {
					if(date == 31 && month != 12) {
						date = 1;
						month++;
					}
					else if(date == 31 && month == 12) {
						date = 1;
						month = 1;
						year++;
					}
				}
				else if(startDate.getMonth() == 4 || startDate.getMonth() == 6 || startDate.getMonth() == 9 || startDate.getMonth() == 11) {
					if(date == 30) {
						date = 1;
						month++;
					}
				}
				else if(startDate.getMonth() == 2 ) {
					if(date == 29) {
						date = 1;
						month++;
					}
				}
				startToEnd.add(new DateSubstitute(date,month, year));
				date++;
			}
				
				
		for(DateSubstitute ds : startToEnd) {
			
			if(!checkIfContains(availableDatesSub, ds)) {
				return false;
			}
	
		}
		
		return true;
	}
	
	public void approveComment(Comment comment) {
		ArrayList<Comment> comments = (ArrayList<Comment>)apartments.get(comment.getApartment().getId()).getComments();
		
		for(Comment c : apartments.get(comment.getApartment().getId()).getComments()) {
			if(comment.getAuthor().getUsername().equals(c.getAuthor().getUsername()) && comment.getContent().equals(c.getContent())) {
				c.setApproved(true);
				break;
			}
		}
		
		saveAllApartments();
	}
	
	public String addComment(Comment comment) {
			try {
				apartments.get(comment.getApartment().getId()).getComments().add(comment);	
				saveAllApartments();
				
				return "STATUS 200 OK";
			}
			catch (Exception e) {
				return "STATUS 400 BAD REQUEST";
			}
	}
	
	public String cancelReservation(Reservation reservation) {
		if(reservation.getReservationStatus() == ReservationStatus.ACCEPTED || reservation.getReservationStatus() == ReservationStatus.CREATED) {
			try {
				ArrayList<Reservation> reservations = (ArrayList<Reservation>) apartments.get(reservation.getReservedApartment().getId()).getReservations();
				
				for(Reservation res : reservations) {
					if(res.getStartDateString().equalsIgnoreCase(reservation.getStartDateString())) {
						res.setReservationStatus(ReservationStatus.DECLINED);
					}
				}
				
				apartments.get(reservation.getReservedApartment().getId()).setReservations(reservations);
				
				apartments.get(reservation.getReservedApartment().getId()).setAvailableDates(returnDates(reservation.getCheckInDate(),reservation.getNumberOfNights(),apartments.get(reservation.getReservedApartment().getId()).getAvailableDates()));
				
				for(Date date : apartments.get(reservation.getReservedApartment().getId()).getAvailableDates()) {
					apartments.get(reservation.getReservedApartment().getId()).getAvailableDatesString().add(date.getYear()+1900+"-"+date.getMonth()+"-"+date.getDate());
				}
				
				saveAllApartments();
		
				return "STATUS 200 OK";
			}
			catch (Exception e) {
				return "STATUS 400 BAD REQUEST";
			}
		}
		else {
			return "STATUS 400 BAD REQUEST";
		}
	}
	
	public ArrayList<Date> returnDates(Date startDate ,int numberOfNights, ArrayList<Date> availableDates) {
		ArrayList<DateSubstitute> availableDatesSub = new ArrayList<DateSubstitute>();
		ArrayList<DateSubstitute> startToEnd = new ArrayList<DateSubstitute>();
		
		for(int i = 0 ; i < availableDates.size() ; i++) {
			availableDatesSub.add(new DateSubstitute(availableDates.get(i).getDate(), availableDates.get(i).getMonth(), availableDates.get(i).getYear()+1900));
		}
		

		
		int date = startDate.getDate();
		int month = startDate.getMonth();
		int year = startDate.getYear();
		
	for(int i = 0 ; i < numberOfNights ; i++) {
		
		if(startDate.getMonth() == 1 || startDate.getMonth() == 3 || startDate.getMonth() == 5 || startDate.getMonth() == 7 || startDate.getMonth() == 8 || startDate.getMonth() == 10 || startDate.getMonth() == 12) {
			if(date == 31 && month != 12) {
				date = 1;
				month++;
			}
			else if(date == 31 && month == 12) {
				date = 1;
				month = 1;
				year++;
			}
		}
		else if(startDate.getMonth() == 4 || startDate.getMonth() == 6 || startDate.getMonth() == 9 || startDate.getMonth() == 11) {
			if(date == 30) {
				date = 1;
				month++;
			}
		}
		else if(startDate.getMonth() == 2 ) {
			if(date == 29) {
				date = 1;
				month++;
			}
		}
		startToEnd.add(new DateSubstitute(date,month, year));
		date++;
	}
		
		ArrayList<DateSubstitute> retValT = availableDatesSub;
		
		int position = 0;

		while(startToEnd.get(0).getDay() > availableDatesSub.get(position).getDay() && startToEnd.get(0).getMonth() > availableDatesSub.get(position).getMonth() && startToEnd.get(0).getYear() > availableDatesSub.get(position).getYear()) {
			position++;
		}
		
		position = position;
			
			for(DateSubstitute ds : startToEnd) {
						retValT.add(position, ds);
						position++;
			}
		
		

		
		

		ArrayList<Date> retVal = new ArrayList<Date>();
		for(DateSubstitute ds : retValT) {
			retVal.add(new Date(ds.getYear() - 1900,ds.getMonth(),ds.getDay()));
		}
		return  retVal;
	}
	
}
