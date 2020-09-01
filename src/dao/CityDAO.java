package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import beans.City;

public class CityDAO {

	private Map<String, City> cities = new HashMap<>();
	private String path;
	
	public CityDAO(){}
	
	public CityDAO(String contextPath) {
		this.path = contextPath;
		loadCities();
	}
	
	private void loadCities() {
		cities.clear();
		
		ObjectMapper objectMapper = new ObjectMapper();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path + "/files/cities.json"));
			String line = br.readLine();
			line.trim();
			while(line != null) {
				City newCity = objectMapper.readValue(line, City.class);
				cities.put(newCity.getPostCode(), newCity);
				line = br.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<City> findAllCities() {
		return cities.values();
	}
}
