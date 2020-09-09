package services;

import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import beans.ReservationInfo;
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
	public boolean addNewApartment(Apartment ap) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		return dao.addNewApartment(ap);
	}
	
	@GET
	@Path("/getAllApartments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getAllApartments(){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		return dao.getAllApartments();
	}
	
	@GET
	@Path("/getApartmentsByUsername")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getApartmentsByUsername(@QueryParam("username") String username){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		return dao.getApartmentsByUsername(username);
	}
	
	@GET
	@Path("/filterApartments")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> filterApartments(@QueryParam("location") String location,@QueryParam("numberOfGuests") int numberOfGuests,@QueryParam("pricePerNightMin") double pricePerNightMin,@QueryParam("pricePerNightMax") double pricePerNightMax,@QueryParam("startDate") String startDate,@QueryParam("endDate") String endDate,@QueryParam("numberOfRoomsMin") int numberOfRoomsMin,@QueryParam("numberOfRoomsMax") int numberOfRoomsMax ,@Context HttpServletRequest request){
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		return dao.filterApartments(location,numberOfGuests,pricePerNightMin,pricePerNightMax,startDate,endDate,numberOfRoomsMin,numberOfRoomsMax);
	}
	
	
	
	@POST
	@Path("/deleteAmenitieInApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteAmenitieInApartments(Amenities am) {
		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		dao.deleteAmenitieInApartments(am);
	}
	
	@POST
	@Path("/bookApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void bookApartment(ReservationInfo reservationInfo) {

		ApartmentDAO dao = (ApartmentDAO) ctx.getAttribute("apartments");
		dao.bookApartment(reservationInfo);
	}
	
	
	
	
}
