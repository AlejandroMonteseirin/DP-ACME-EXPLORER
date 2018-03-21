package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	public SocialIdentity() {
		super();
	}

	//Attributes
	private String nick;
	private String socialNetworkName;
	private String profileLink;
	private String photoUrl;

	@NotBlank
	public String getNick() {
		return nick;
	}

	@NotBlank
	public String getSocialNetworkName() {
		return socialNetworkName;
	}

	@URL
	public String getProfileLink() {
		return profileLink;
	}

	@URL
	public String getPhotoUrl() {
		return photoUrl;
	}


	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setSocialNetworkName(String socialNetworkName) {
		this.socialNetworkName = socialNetworkName;
	}

	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	//Relationships
	
	private Actor actor;
	
	@Valid
	@ManyToOne(optional=false)
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}


}
