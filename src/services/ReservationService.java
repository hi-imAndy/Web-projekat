package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Reservation;
import dao.ReservationsDAO;

@Path("/reservations")
public class ReservationService {
	@Context
	ServletContext ctx;
	
	public ReservationService() {}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("reservations") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("reservations", new ReservationsDAO(contextPath));
		}
	}
	
	@POST
	@Path("/addNewReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addNewReservation(Reservation r) {
		ReservationsDAO dao = (ReservationsDAO) ctx.getAttribute("reservations");
		return dao.addNewReservation(r);
	}
	
	@GET
	@Path("/getAllReservations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Reservation> getAllReservations(){
		ReservationsDAO dao = (ReservationsDAO) ctx.getAttribute("reservations");
		return dao.getAllReservations();
	}
}
