package dao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Apartment;

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
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(path + "/files/apartments.json"), ap);
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
}
