package beans;

import java.util.List;

import enums.ApartmentStatus;
import enums.ApartmentType;

public class FilterInfoHost {
	private ApartmentType type;
	private ApartmentStatus status;
	private List<Amenities> amenities;
	
	public FilterInfoHost() {}

	public FilterInfoHost(ApartmentType type, ApartmentStatus status, List<Amenities> amenities) {
		this.type = type;
		this.status = status;
		this.amenities = amenities;
	}

	public ApartmentType getType() {
		return type;
	}

	public void setType(ApartmentType type) {
		this.type = type;
	}

	public ApartmentStatus getStatus() {
		return status;
	}

	public void setStatus(ApartmentStatus status) {
		this.status = status;
	}

	public List<Amenities> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenities> amenities) {
		this.amenities = amenities;
	}

	@Override
	public String toString() {
		return "FilterInfoHost [type=" + type + ", status=" + status + ", amenities=" + amenities + "]";
	}
	
	
}
