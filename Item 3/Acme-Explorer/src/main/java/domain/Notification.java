package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Notification extends DomainEntity {

	public Notification() {
		super();
	}

	private String body;
	private Administrator sender;
	//private Actor recipient;

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Administrator getSender() {
		return sender;
	}

	public void setSender(Administrator sender) {
		this.sender = sender;
	}
//	@Valid
//	@ManyToOne(optional=true)
//	public Actor getRecipient() {
//		return recipient;
//	}
//
//	public void setRecipient(Actor recipient) {
//		this.recipient = recipient;
//	}

}
