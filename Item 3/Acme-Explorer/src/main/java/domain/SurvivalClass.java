package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SurvivalClass extends DomainEntity{

	public SurvivalClass() {
	}

	//Attributes
	private String title;
	private String description;
	private Date organizationDate;
	

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getOrganizationDate() {
		return organizationDate;
	}

	public void setOrganizationDate(Date organizationDate) {
		this.organizationDate = organizationDate;
	}

	//Relationships
	private Location location;
	private Trip trip;
	private Collection<Explorer> explorers;


	@Valid
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Valid
	@OneToOne 
	@NotNull
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	@Valid
	@ManyToMany
	public Collection<Explorer> getExplorers() {
		return explorers;
	}

	public void setExplorers(Collection<Explorer> explorers) {
		this.explorers = explorers;
	}
	
	

}
