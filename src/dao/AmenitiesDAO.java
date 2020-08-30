package dao;

import java.io.BufferedReader;
import java.io.FileReader;
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
}
