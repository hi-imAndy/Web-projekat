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

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Address;
import beans.Amenities;
import beans.Apartment;
import beans.City;
import beans.Location;

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
	
	public Collection<Apartment> getAllApartments(){
		ArrayList<String> lista = new ArrayList<String>();
		Location lokacija = new Location(11, 11, new Address("Ulica", 11, new City("11", "Novi Sad")));
		lista.add("2wCEAAkGBxMSEhUSExIVFhUXGBkYGBgYGRsaGhoaGBcdHRodHxcaHSggHR8lHRcbITEhJSkrLi4uGx8zODUtNygtLisBCgoKDg0OGxAQGy8lICUtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf");
		lista.add("https://www.ekapija.com/thumbs/novi_sad_051217_tw630.jpg");
		//apartments.put("PROBNI ID1", new Apartment("PROBA 1", null, 1, 1, null, null, null, null, null, lista, 11, null, null, true, null, null));
		//apartments.put("PROBNI ID2", new Apartment("PROBA 2", null, 1, 1, null, null, null, null, null, lista, 11, null, null, true, null, null));
		//apartments.put("PROBNI ID3", new Apartment("PROBA 3", null, 1, 1, null, null, null, null, null, lista, 11, null, null, true, null, null));
		return apartments.values();
	}
	
	private boolean compareDates(Date startDate ,Date endDate,ArrayList<Date> availableDates) {
		
		int differenceDay = endDate.getDate() - startDate.getDate();
		int differenceMonth = endDate.getMonth() - startDate.getMonth();
		int addDay = 0;
		int addMonth = 0;
		
	if(differenceDay > 0 && differenceMonth == 0) {
		try {
			for(int i = 0 ; i < availableDates.size() ; i++) {
				if(availableDates.get(i).getDate() == startDate.getDate() + addDay && availableDates.get(i).getMonth() + addMonth == startDate.getMonth() && availableDates.get(i).getYear() == startDate.getYear()) {
					if(addDay != differenceDay) {
						addDay++;
					}
					else {
						return true;
					}
				}
				else
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	if(differenceDay > 0 && differenceMonth > 0) {
		try {
			for(int i = 0 ; i < availableDates.size() ; i++) {
				if(availableDates.get(i).getDate() == startDate.getDate() + addDay && availableDates.get(i).getMonth() + addMonth == startDate.getMonth() && availableDates.get(i).getYear() == startDate.getYear()) {
					if(addDay != differenceDay) {
						addDay++;
					}
					else {
						return true;
					}
				}
				else
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	if(differenceDay < 0 && differenceMonth > 0) {
		try {
			for(int i = 0 ; i < availableDates.size() ; i++) {
				if(availableDates.get(i).getDate() == startDate.getDate() + addDay && availableDates.get(i).getMonth() + addMonth == startDate.getMonth() && availableDates.get(i).getYear() == startDate.getYear()) {
					if(addDay != differenceDay) {
						addDay++;
					}
					else {
						return true;
					}
				}
				else
					return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
		return true;
	}
	
	
	public Collection<Apartment> filterApartments(String location,int numberOfGuests,double pricePerNightMin,double pricePerNightMax , Date startDate,Date endDate , int numberOfRoomsMin , int numberOfRoomsMax){
		Map<String, Apartment> returnValue = new HashMap<>();
		
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
		
		
		
		
		//LOKACIJA 
			if(location != null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location))
						returnValue.put(ap.getId(),ap);
				}
			}	
		
		//LOKACIJA + SOBE
			if(location != null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//DATUM 
			if(location == null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
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
		
		//DATUM + SOBE
			if(location == null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
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
			
		//LOKACIJA + CENA
			if(location != null && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//LOKACIJA + CENA + SOBE
			if(location != null && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}		
			
		//LOKACIJA + DATUM
			if(location != null && numberOfGuests == 0 && pricePerNightMin == -1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location))
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
		
			//LOKACIJA + DATUM + SOBE
			if(location != null && numberOfGuests == 0 && pricePerNightMin==-1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
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
			
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU + SOBE
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU + DATUM
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
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
		
		//BROJ GOSTIJU + LOKACIJA + CENA PO NOCENJU + DATUM + SOBE
			if(location != null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
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
			
		//BROJ GOSTIJU + LOKACIJA
			if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests )
						returnValue.put(ap.getId(),ap);
				}
			}
		
		//BROJ GOSTIJU + LOKACIJA + SOBE
			if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + LOKACIJA + DATUM
		if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests )
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
		
		//BROJ GOSTIJU + LOKACIJA + DATUM + SOBE
		if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
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
		
		// LOKACIJA + CENA + DATUM
		if(location != null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
			for(Apartment ap : apartments.values()) {
				if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
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
		
		
		// LOKACIJA + GOSTI + DATUM + SOBE
		if(location != null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
			for(Apartment ap : apartments.values()) {
				if(ap.getLocation().getAddress().getCity().getName().equalsIgnoreCase(location) && ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax )
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
		
		//BROJ GOSTIJU
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests)
						returnValue.put(ap.getId(),ap);
				}
			}	
		
		//BROJ GOSTIJU + SOBE
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + DATUM
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests)
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
			
		//BROJ GOSTIJU + DATUM + SOBE
			if(location == null  && numberOfGuests != 0 && pricePerNightMin==-1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
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
			
		// BROJ GOSTIJU + CENA NOCENJA	
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1 ) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
		
		// BROJ GOSTIJU + CENA NOCENJA	+ SOBE
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}	
			
		//BROJ GOSTIJU + CENA NOCENJA + DATUM
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1 ) {
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
			if(location == null  && numberOfGuests != 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if(ap.getNumberOfGuests() == numberOfGuests && ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)

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
			
		//CENA PO NOCENJU
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}
			
		//CENA PO NOCENJU + SOBE
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
						returnValue.put(ap.getId(),ap);
				}
			}
			
		//SOBE
			if(location == null  && numberOfGuests == 0 && pricePerNightMin == -1 && startDate == null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin == -1) {
				for(Apartment ap : apartments.values()) {
					if(  ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax)
						returnValue.put(ap.getId(),ap);
				}
			}
			
		//CENA PO NOCENJU + DATUM
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
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
			
			//CENA PO NOCENJU + DATUM
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin == -1 && numberOfRoomsMax == -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin && ap.getPricePerNight() <= pricePerNightMax)
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
			
			
			//CENA PO NOCENJU + DATUM + SOBE
			if(location == null  && numberOfGuests == 0 && pricePerNightMin != -1 && startDate != null && numberOfRoomsMin != -1 &&  numberOfRoomsMax != -1 && pricePerNightMin != -1) {
				for(Apartment ap : apartments.values()) {
					if( ap.getPricePerNight() >= pricePerNightMin&& ap.getNumberOfRooms() >= numberOfRoomsMin && ap.getNumberOfRooms() <= numberOfRoomsMax && ap.getPricePerNight() <= pricePerNightMax)
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
		return returnValue.values();
	}
	
}
