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
import java.util.Map;
import java.util.function.IntPredicate;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Address;
import beans.Amenities;
import beans.Apartment;
import beans.City;
import beans.DateSubstitute;
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
		private ArrayList<Date> deleteDates(Date startDate ,Date endDate, ArrayList<Date> availableDates) {
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
			
			ArrayList<DateSubstitute> retValT = new ArrayList<DateSubstitute>();
			
			for(DateSubstitute ds : availableDatesSub) {
					if(!checkIfContains(startToEnd, ds))
						retValT.add(ds);
			}

			ArrayList<Date> retVal = new ArrayList<Date>();
			for(DateSubstitute ds : retValT) {
				retVal.add(new Date(ds.getYear() - 1900,ds.getMonth(),ds.getDay()));
				System.out.println(ds.getYear()+"."+ds.getMonth()+"."+	ds.getDay());
			}
			return  retVal;
		}

	public void bookApartment(ReservationInfo reservationInfo) {
		
		
		User user = reservationInfo.getUser();
		String startDateString = reservationInfo.getStartDate();
		String endDateString = reservationInfo.getEndDate();
		String reservationMessage = reservationInfo.getReservationMessage();
		
		Date startDate = new Date(Integer.parseInt(startDateString.split("-")[0]),Integer.parseInt(startDateString.split("-")[1]),Integer.parseInt(startDateString.split("-")[2]));
		Date endDate = new Date(Integer.parseInt(endDateString.split("-")[0]),Integer.parseInt(endDateString.split("-")[1]),Integer.parseInt(endDateString.split("-")[2]));
		UserDAO userDao = new UserDAO(); 
		int numberOfNights = 0;
		if(endDate.getDate() - startDate.getDate() != 0 && endDate.getMonth() - startDate.getMonth() == 0) {
			numberOfNights = endDate.getDate() - startDate.getDate();
		}
		else if(endDate.getMonth() - startDate.getMonth() == 0) {
			if(startDate.getMonth() == 1 || startDate.getMonth() == 3 || startDate.getMonth() == 5 || startDate.getMonth() == 7 || startDate.getMonth() == 8 || startDate.getMonth() == 10 || startDate.getMonth() == 12) {
			for(int i = 0 ; i <= 31 ; i++) {
				numberOfNights = i;
				if(startDate.getDate()+i == 31)
					break;
			}
			for(int i = 0 ; i <= endDate.getDate() ; i++) {
				numberOfNights += i;
			}
		}
			else if(startDate.getMonth() == 4 || startDate.getMonth() == 6 || startDate.getMonth() == 9 || startDate.getMonth() == 11 ) {
				for(int i = 0 ; i <= 30 ; i++) {
					numberOfNights = i;
					if(startDate.getDate()+i == 31)
						break;
				}
				for(int i = 0 ; i <= endDate.getDate() ; i++) {
					numberOfNights += i;
				}
			}
		}	
		
		
		Reservation reservation = new Reservation(reservationInfo.getApartment(), startDate, numberOfNights, reservationInfo.getApartment().getPricePerNight()*numberOfNights, reservationMessage, user , ReservationStatus.CREATED);
		
		//OVU LINIJU TREBA OBRISATI KADA SE SREDE OBJEKTI
		apartments.get(reservationInfo.getApartment().getId()).setReservations(new ArrayList<Reservation>());
		
		apartments.get(reservationInfo.getApartment().getId()).getReservations().add(reservation);
		
		ArrayList<Date> availableDates = (ArrayList<Date>) apartments.get(reservationInfo.getApartment().getId()).getAvailableDates();
		apartments.get(reservationInfo.getApartment().getId()).setAvailableDates(deleteDates(startDate, endDate, availableDates));
	}
	
}
