package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {


	public Message() {
		super();
	}

	//Attributes
	private Date moment;
	private String subject;
	private String body;
	private String priority;


	//@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return moment;
	}

	@NotBlank
	public String getSubject() {
		return subject;
	}

	@NotBlank
	public String getBody() {
		return body;
	}

	@Pattern(regexp = "(^HIGH|NEUTRAL|LOW$)")
	public String getPriority() {
		return priority;
	}


	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	//Relationships
	private Actor recipient;
	private Actor sender;

	@Valid
	@NotNull
	@ManyToOne(optional=true)
	public Actor getRecipient() {
		return recipient;
	}

	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Actor getSender() {
		return sender;
	}

	public void setSender(Actor sender) {
		this.sender = sender;
	}



}
