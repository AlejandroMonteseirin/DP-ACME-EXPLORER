package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

	public Audit() {
		super();
	}

	//Attributes
	private Date auditionMoment;
	private String title;
	private String description;
	private Collection<String> attachmentURLs;
	private String savedMode;
	
	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getAuditionMoment() {
		return auditionMoment;
	}

	public void setAuditionMoment(Date auditionMoment) {
		this.auditionMoment = auditionMoment;
	}

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
	
		//Relationships
		private Trip trip;
		private Auditor auditor;
		
		
	@Valid
	@ManyToOne(optional=false)
	@NotNull
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	@Valid
	@ManyToOne(optional=true)
	public Auditor getAuditor(){
		return auditor;
	}
	
	public void setAuditor(Auditor auditor){
		this.auditor = auditor;
	}

	@ElementCollection
	public Collection<String> getAttachmentURLs() {
		return attachmentURLs;
	}

	public void setAttachmentURLs(Collection<String> attachmentURLs) {
		this.attachmentURLs = attachmentURLs;
	}

	@NotNull
	@Pattern(regexp = "(^DRAFT MODE|FINAL MODE$)")
	public String getSavedMode() {
		return savedMode;
	}

	public void setSavedMode(String savedMode) {
		this.savedMode = savedMode;
	}

}