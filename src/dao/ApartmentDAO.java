package dao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Apartment;
import beans.User;

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
	
	public void saveApartment(Apartment ap) {
		try {
			File file = new File(path + "files/apartments.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(""); //TODO: Zavrsiti metodu
			pw.flush();
			pw.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadApartments() {
		BufferedReader in = null;
		try {
			
			File file = new File(this.path + "/files/apartments.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while((line = in.readLine()) != null) {
				line = line.trim();
				if(line.equals("") || line.indexOf('#') ==0) 
					continue;
				st = new StringTokenizer(line,";");
				while(st.hasMoreTokens()) {
					//TODO: zavrsiti funkciju
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
}
