package dao;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Address;
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
	
	public void addNewApartment(Apartment ap) {
		apartments.put(ap.getId(), ap);
		saveApartment(ap);
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
}
