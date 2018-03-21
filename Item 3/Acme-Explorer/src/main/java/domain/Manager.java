package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {
	
	public Manager() {
		super();
	}

	// Relationships
	private Collection<Trip> trips;
	private Collection<SurvivalClass> survivalClasses;
	private Collection<Note> notes;
	private Collection<Reply> replies;


	@Valid
	@OneToMany(mappedBy="manager", cascade = CascadeType.ALL)
	public Collection<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Collection<Reply> replies) {
		this.replies = replies;
	}

	@Valid
	@OneToMany
	public Collection<SurvivalClass> getSurvivalClasses() {
		return survivalClasses;
	}

	public void setSurvivalClasses(Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

	@Valid
	@OneToMany
	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}


	@Valid
	@OneToMany
	public Collection<Trip> getTrips() {
		return trips;
	}

	public void setTrips(Collection<Trip> trips) {
		this.trips = trips;
	}

	
}
