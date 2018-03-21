package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;


@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {
	
	public Explorer() {
		super();
	}
	
	//Relationships
	private Collection<Contact> contacts;
	private Collection<Application> applications;

	
	@OneToMany
	@Valid
	public Collection<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Collection<Contact> contacts) {
		this.contacts = contacts;
	}

	@Valid
	@OneToMany(mappedBy="explorer", cascade=CascadeType.ALL)
	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

}
