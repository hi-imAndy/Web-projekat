package beans;

import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Role;

public class Host extends User {

	private List<Apartment> apartmentsForRent;

	public Host(String username, String password, String firstName, String lastName, Gender gender, Role role) {
		super(username, password, firstName, lastName, gender, role);
		this.apartmentsForRent = new ArrayList<Apartment>();
	}
	
	public Host(String username, String password, String firstName, String lastName, Gender gender, Role role,
			List<Apartment> apartmentsForRent) {
		super(username, password, firstName, lastName, gender, role);
		this.apartmentsForRent = apartmentsForRent;
	}

	public List<Apartment> getApartmentsForRent() {
		return apartmentsForRent;
	}

	public void setApartmentsForRent(List<Apartment> apartmentsForRent) {
		this.apartmentsForRent = apartmentsForRent;
	}
	
	
	
}
