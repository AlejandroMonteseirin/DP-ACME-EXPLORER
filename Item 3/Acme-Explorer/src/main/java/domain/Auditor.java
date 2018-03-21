package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor{

	//Relationships
	private Collection<Audit> audits;
	private Collection<Note> notes;

	public Auditor() {
		super();
	}

	@Valid
	@OneToMany(mappedBy="auditor")
	public Collection<Audit> getAudits() {
		return audits;
	}

	public void setAudits(Collection<Audit> audits) {
		this.audits = audits;
	}

	
	@Valid
	@OneToMany(mappedBy="auditor")
	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}



}
