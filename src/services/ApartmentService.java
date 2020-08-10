package services;

import javax.annotation.PostConstruct;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Apartment;
import dao.ApartmentDAO;

@Path("/apartments")
public class ApartmentService {

	@Context
	ServletContext ctx;
	
	public ApartmentService() {}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("apartments") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("apartments", new ApartmentDAO(contextPath));
		}
	}
	
	@POST
	@Path("/addNewApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addNewApartment(Apartment ap) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		dao.addNewApartment(ap);
	}
	
}
