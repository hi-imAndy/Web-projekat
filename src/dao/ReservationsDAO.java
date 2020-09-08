package dao;
import beans.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class ReservationsDAO {

	private Map<String, Reservation> reservations = new HashMap<>();
	private String path;
	
	public ReservationsDAO() {}
	
	public ReservationsDAO(String contextPath) {
		this.path = contextPath;
		loadReservations();
	}
	
	private void loadReservations() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path + "/files/reservations.json"));
			String line = br.readLine();
			while(line != null) {
				Reservation newReservation = objectMapper.readValue(line, Reservation.class);
				reservations.put(newReservation.getId(), newReservation);
				line = br.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean addNewReservation(Reservation r) {
		if(reservations.containsKey(r.getId())) {
			return false;
		}
		reservations.put(r.getId(), r);
		saveReservation(r);
		return true;
	}
	
	private void saveReservation(Reservation r) {
		
		reservations.put(r.getId(), r);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String rAsString = objectMapper.writeValueAsString(r);
			FileWriter fileWriter = new FileWriter(path + "files\\reservations.json", true);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.print(rAsString);
		    printWriter.print("\n");
			printWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<Reservation> getAllReservations(){
		return reservations.values();
	}
}
