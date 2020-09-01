package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.City;
import dao.CityDAO;

@Path("/cities")
public class CityService {

	@Context
	ServletContext ctx;
	
	public CityService() {}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("cities") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("cities", new CityDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getAllCities")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<City> getAllCities(){
		CityDAO dao = (CityDAO)ctx.getAttribute("cities");
		return dao.findAllCities();
	}
}
