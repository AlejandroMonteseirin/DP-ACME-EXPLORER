package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	//Attributes
	private String fullName;
	private String email;
	private String phoneNumber;
	private String linkedInProfileUrl;
	private String comment;

	public EndorserRecord() {
		super();
	}

	public String getComment() {
		return comment;
	}
 
	public void setComment(String comment) {
		this.comment = comment;
	}

	@NotBlank
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotNull
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@URL
	public String getLinkedInProfileUrl() {
		return linkedInProfileUrl;
	}

	public void setLinkedInProfileUrl(String linkedInProfileUrl) {
		this.linkedInProfileUrl = linkedInProfileUrl;
	}

	

}
