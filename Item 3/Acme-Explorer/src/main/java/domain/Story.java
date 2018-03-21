package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;



import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Story extends DomainEntity {

	public Story() {
		super();
	}

	//Attributes
	private String title;
	private String text;
	private Collection<String> attachmentURLs;
	private Trip trip;

	

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	@ElementCollection
	public Collection<String> getAttachmentURLs() {
		return attachmentURLs;
	}

	public void setAttachmentURLs(Collection<String> attachmentURLs) {
		this.attachmentURLs = attachmentURLs;
	}
	
	//relationships
	
	private Explorer explorer;
	
	
	
	
	@Valid
	@ManyToOne(optional = false)
	public Explorer getExplorer() {
		return explorer;
	}

	public void setExplorer(Explorer explorer) {
		this.explorer = explorer;
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
}
