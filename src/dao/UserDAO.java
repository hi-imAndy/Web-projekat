package dao;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import beans.Amenities;
import beans.Apartment;
import beans.Reservation;
import beans.User;
import enums.Gender;
import enums.Role;

public class UserDAO {

	private Map<String, User> users = new HashMap<>();
	private String path;
	
	public UserDAO() {
		
	}
	
	public UserDAO(String contextPath) {
		this.path = contextPath;
		loadUsers();
	}
	
	public User find(String username, String password) {
		if (!users.containsKey(username)) {
			return null;
		}
		User user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
	

	public Collection<User> searchUsers(String username, Role role, Gender gender, boolean u, boolean r, boolean g){
		Set<User> usersByUsername = new HashSet<User>();
		Set<User> usersByRole = new HashSet<User>();
		Set<User> usersByGender = new HashSet<User>();
		
		for(User user : users.values()) {
			if(user.getUsername().contains(username))
				usersByUsername.add(user);
			if(user.getRole().equals(role))
				usersByRole.add(user);
			if(user.getGender().equals(gender))
				usersByGender.add(user);
		}
		
		Set<User> ret_users;
		
		if(u) {
			ret_users = new HashSet<User>(usersByUsername);
			if(r)
				ret_users.retainAll(usersByRole);
			if(g)
				ret_users.retainAll(usersByGender);
		}else {
			if(r) {
				ret_users = new HashSet<User>(usersByRole);
				if(g)
					ret_users.retainAll(usersByGender);
			}else {
				ret_users = new HashSet<User>(usersByGender);
			}
		}
		
		return ret_users;
	}
	
	public User findByUsername(String username) {
		return users.get(username);
	}
	
	public Collection<User> findAll() {
		return users.values();
	}
	
	public void saveUser(User user) {
		
		users.put(user.getUsername(), user);
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String userAsString = objectMapper.writeValueAsString(user);
			FileWriter fileWriter = new FileWriter(path + "files\\users.json", true);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.print(userAsString);
		    printWriter.print("\n");
			printWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadUsers() {
		
		users.clear();
		
		ObjectMapper objectMapper = new ObjectMapper();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path + "/files/users.json"));
			String line = br.readLine();
			line.trim();
			while(line != null) {
				User newUser = objectMapper.readValue(line, User.class);
				users.put(newUser.getUsername(), newUser);
				line = br.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(User user , String username) {
		users.replace(username, user);
	}
	
	public void addReservation(Reservation reservation , User user) {
		//users.get(user.getUsername()).setReservations(new ArrayList<Apartment>());
		//users.get(user.getUsername()).getReservations().add(new ApartmentDAO().getApartmentById(reservation.getReservedApartment()));
	}
	
	public boolean changeUserData(String username, String oldPassword , String password, String passwordConfirm ,String firstName,String lastName) {
			for(int i = 0 ; i < 100 ; i++) {
				System.out.println("PASSWORD: " + password);
			}
			for(int i = 0 ; i < 100 ; i++) {
				System.out.println("CONFIRM: " + passwordConfirm);
			}


			
			User u = findByUsername(username);
			if(password != null || passwordConfirm != null) {
				if(!password.equals(passwordConfirm))
						return false;
				else if(password.length() < 8 || passwordConfirm.length() < 8) 
					return false;			
				else 
					u.setPassword(password);
	}
				u.setFirstName(firstName);
				u.setLastName(lastName);
				updateUser(u, username);
	
				return true;
			
		
	}
	

}
