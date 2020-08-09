package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import dao.UserDAO;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;
	
	public UserService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("users") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("users", new UserDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		return dao.findAll();
	}
	
	@GET
	@Path("/findByUsername")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(String username) {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		return dao.findByUsername(username);
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User register(User user) {
		saveUser(user);
		return user;
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request, User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		User loggedUser = dao.find(user.getUsername(), user.getPassword());
		if(loggedUser != null) {
			request.getSession().setAttribute("user", loggedUser);
		}
		return loggedUser;
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	private void saveUser(User user) {
		try {
			
			FileWriter fw = new FileWriter("files/users.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(user.getUsername() + ";" + user.getPassword() + ";" + user.getFirstName() + ";" + user.getLastName() + ";" +"MALE" + ";" + "GUEST" );
			pw.flush();
			pw.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
