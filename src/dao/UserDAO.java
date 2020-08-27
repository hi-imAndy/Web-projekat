package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

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
		List<User> ret_users = new ArrayList<User>();
		
		if(u) {
			if(r) {
				if(g) {
					for(User user : users.values()) {
						if(user.getUsername().contains(username) && user.getRole() == role && user.getGender() == gender) {
							ret_users.add(user);
						}
					}
				}else {
					for(User user : users.values()) {
						if(user.getUsername().contains(username) && user.getRole() == role) {
							ret_users.add(user);
						}
					}
				}
			}else {
				if(g) {
					for(User user : users.values()) {
						if(user.getUsername().contains(username) && user.getGender() == gender) {
							ret_users.add(user);
						}
					}
				}else {
					for(User user : users.values()) {
						if(user.getUsername().contains(username)) {
							ret_users.add(user);
						}
					}
				}
			}
		}else {
			if(r) {
				if(g) {
					for(User user : users.values()) {
						if(user.getRole() == role && user.getGender() == gender) {
							ret_users.add(user);
						}
					}
				}else {
					for(User user : users.values()) {
						if(user.getRole() == role) {
							ret_users.add(user);
						}
					}
				}
			}else {
				if(g) {
					for(User user : users.values()) {
						if(user.getGender() == gender) {
							ret_users.add(user);
						}
					}
				}
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
			//System.out.println(userAsString);
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
	
	public boolean changeUserData(String username, String oldPassword , String password, String passwordConfirm ,String firstName,String lastName) {

		if(password != null && passwordConfirm != null)
			if(password != passwordConfirm && !password.isEmpty() && !passwordConfirm.isEmpty() ) {

				return false;
			}
			
			User u = findByUsername(username);
			if(password != null && passwordConfirm != null)
				if(!password.isEmpty() && !passwordConfirm.isEmpty() )
					u.setPassword(password);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			updateUser(u, username);


			return true;
		
		
	}
	

}
