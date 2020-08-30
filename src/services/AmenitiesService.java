package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Amenities;
import dao.AmenitiesDAO;

@Path("/amenities")
public class AmenitiesService {

	@Context
	ServletContext ctx;
	
	public AmenitiesService() {}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("amenities") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("amenities", new AmenitiesDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getAllAmenities")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Amenities> getAllAmenities(){
		AmenitiesDAO dao = (AmenitiesDAO)ctx.getAttribute("amenities");
		return dao.findAll();
	}
}
