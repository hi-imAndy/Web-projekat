package services;

import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Amenities;
import beans.Apartment;
import beans.User;
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
	public void addNewApartment(Apartment ap) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		dao.addNewApartment(ap);
	}
	
	@GET
	@Path("/getAllApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getAllApartments(){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		return dao.getAllApartments();
	}
	
	@GET
	@Path("/filterApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> filterApartments(@QueryParam("location") String location,@QueryParam("numberOfGuests") int numberOfGuests,@QueryParam("pricePerNight") double pricePerNight,@QueryParam("testDate") Date testDate,@QueryParam("numberOfRooms") int numberOfRooms){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		return dao.filterApartments(location,numberOfGuests,pricePerNight,testDate,numberOfRooms);
	}
	
	
	
	@POST
	@Path("/deleteAmenitieInApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteAmenitieInApartments(Amenities am) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		dao.deleteAmenitieInApartments(am);
	}
}
