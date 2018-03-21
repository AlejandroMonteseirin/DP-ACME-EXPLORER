package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	private String title;
	private String body;
	private String applicableLaws;
	private Date moment;
	private String savedMode;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@NotEmpty
	public String getApplicableLaws() {
		return applicableLaws;
	}

	public void setApplicableLaws(String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Past
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public LegalText() {
		super();
	}

	@NotNull
	@Pattern(regexp = "(^DRAFT MODE|FINAL MODE$)")
	public String getSavedMode() {
		return savedMode;
	}

	public void setSavedMode(String savedMode) {
		this.savedMode = savedMode;
	}
	
	//Relationships
	
	private Collection<Trip> trips;

	@Valid
	@OneToMany
	public Collection<Trip> getTrips() {
		return trips;
	}

	public void setTrips(Collection<Trip> trips) {
		this.trips = trips;
	}
	
	

}
