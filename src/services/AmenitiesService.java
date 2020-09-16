package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
		List<Amenities> allAmenities = new ArrayList<Amenities>(dao.findAll());
		List<Amenities> notDeletedAmenities = new ArrayList<Amenities>();
		for(Amenities am : allAmenities){
			if(am.getDeleted().equals("NO"))
				notDeletedAmenities.add(am);
		}
		return notDeletedAmenities;
	}
	
	@POST
	@Path("/deleteAmenitie")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteAmenitie(Amenities am) {
		AmenitiesDAO dao = (AmenitiesDAO)ctx.getAttribute("amenities");
		return dao.deleteAmenitie(am);
	}
	
	@POST
	@Path("/editAmenitie")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean editAmenitie(Amenities am) {
		AmenitiesDAO dao = (AmenitiesDAO)ctx.getAttribute("amenities");
		return dao.editAmenitie(am);
	}
	
	@POST
	@Path("/addAmenitie")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addAmenitie(Amenities am) {
		AmenitiesDAO dao = (AmenitiesDAO)ctx.getAttribute("amenities");
		return dao.addAmenitie(am);
	}
}
