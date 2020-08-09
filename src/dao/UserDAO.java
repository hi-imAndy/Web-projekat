package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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
	
	public User findByUsername(String username) {
		return users.get(username);
	}
	
	public Collection<User> findAll() {
		return users.values();
	}
	
	public void saveUser(User user) {
		users.put(user.getUsername(), user);
		try {
			File file = new File(path + "files/users.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(user.getUsername() + ";" + user.getPassword() + ";" + user.getFirstName() + ";" + user.getLastName() + ";" +"MALE" + ";" + "GUEST" );
			pw.flush();
			pw.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadUsers() {
		BufferedReader in = null;
		try {
			File file = new File(path + "/files/users.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					String firstName= st.nextToken().trim();
					String lastName = st.nextToken().trim();
					String genderString = st.nextToken().trim();
					String roleString = st.nextToken().trim();
					
					Gender gender = genderString.equals("MALE") ? Gender.MALE : Gender.FEMALE;
					
					Role role;
					switch(roleString) {
					case "HOST":
						role = Role.HOST;
						break;
					case "GUEST":
						role = Role.GUEST;
						break;
					case "ADMINISTRATOR":
						role = Role.ADMINISTRATOR;
						break;
					default:
						role = Role.GUEST;
					}
					users.put(username, new User(username,password,firstName,lastName,gender,role));
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
}
