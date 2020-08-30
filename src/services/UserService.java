package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import dao.UserDAO;
import enums.Gender;
import enums.Role;

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
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		if(dao.findByUsername(user.getUsername()) == null) {
			dao.saveUser(user);
			return "OK";
		}
		return "ERROR";
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@QueryParam("username") String username,@QueryParam("password") String password, @Context HttpServletRequest request) {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		User loggedUser = dao.find(username, password);
		if(loggedUser != null)
			request.getSession().setAttribute("loggedUser", loggedUser);
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
		return (User) request.getSession().getAttribute("loggedUser");
	}

	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		return dao.findAll();
	}
	
	
	@GET
	@Path("/searchUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> searchUsers(@QueryParam("username") String username, @QueryParam("role") Role role,@QueryParam("gender") Gender gender,@QueryParam("u") boolean u,@QueryParam("r") boolean r,@QueryParam("g") boolean g) {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		return dao.searchUsers(username, role, gender, u, r, g);
	}
	
	@GET
	@Path("/updateAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateUser(@QueryParam("username") String username,@QueryParam("oldPassword") String oldPassword,@QueryParam("password") String password,@QueryParam("confirmPassword") String confirmPassword,@QueryParam("firstName") String firstName,@QueryParam("lastName") String lastName) {
		UserDAO dao = (UserDAO) ctx.getAttribute("users");
		return dao.changeUserData(username, oldPassword, password, confirmPassword, firstName, lastName);
	}
	
}
	

