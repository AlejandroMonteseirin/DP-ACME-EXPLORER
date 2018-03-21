package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
@Entity
@Access(AccessType.PROPERTY)
public class PersonalRecord extends DomainEntity {

	public PersonalRecord() {
		super();
	}

	//Attributes
	private String fullName;
	private String photoUrl;
	private String email;
	private String phoneNumber;
	private String linkedInProfileUrl;


	@NotBlank
	public String getFullName() {
		return fullName;
	}

	@URL
	@NotBlank
	public String getPhotoUrl() {
		return photoUrl;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}

	@NotBlank
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@URL
	@NotBlank
	public String getLinkedInProfileUrl() {
		return linkedInProfileUrl;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setLinkedInProfileUrl(String linkedInProfileUrl) {
		this.linkedInProfileUrl = linkedInProfileUrl;
	}
	
	
}
