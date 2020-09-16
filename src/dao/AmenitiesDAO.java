package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Amenities;

public class AmenitiesDAO {
	private Map<Integer, Amenities> amenities = new HashMap<>();
	private String path;
	
	public AmenitiesDAO() {}
	
	public AmenitiesDAO(String contextPath) {
		this.path = contextPath;
		loadAmenities();
	}
	
	public void loadAmenities() {
		amenities.clear();
		ObjectMapper objectMapper = new ObjectMapper();
		BufferedReader br;
		try {
			
			br = new BufferedReader(new FileReader(path + "/files/amenities.json"));
			String line = br.readLine();
			line.trim();
			while(line!=null) {
				Amenities am = objectMapper.readValue(line, Amenities.class);
				amenities.put(am.getId(), am);
				line = br.readLine();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<Amenities> findAll(){
		return amenities.values();
	}
	
	public boolean deleteAmenitie(Amenities am) {
		if(amenities.containsKey(am.getId())) {
			amenities.get(am.getId()).setDeleted("YES");
			saveAllAmenities();
			return true;
		}
		return false;
	}
	
	public boolean addAmenitie(Amenities am) {
		if(amenities.containsKey(am.getId())) {
			return false;
		}
		amenities.put(am.getId(), am);
		
		saveAmenitie(am);
		
		return true;
	}
	
	private void saveAmenitie(Amenities am) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String amAsString = objectMapper.writeValueAsString(am);
			FileWriter fileWriter = new FileWriter(path + "files\\amenities.json", true);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.print(amAsString);
		    printWriter.print("\n");
			printWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveAllAmenities() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			FileWriter fileWriter = new FileWriter(path + "files\\amenities.json", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			for(Amenities am : amenities.values()) {
				String amAsString = objectMapper.writeValueAsString(am);
				printWriter.print(amAsString);
			    printWriter.print("\n");
			}
			printWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean editAmenitie(Amenities am) {
		if(amenities.containsKey(am.getId())) {
			amenities.replace(am.getId(), am);
			saveAllAmenities();
			return true;
		}
		return false;
	}
}
