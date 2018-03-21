package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	public Sponsorship() {
		super();
	}

	private String bannerURL;
	private String infoPageLink;
	private CreditCard creditCard;

	@URL
	@NotBlank
	public String getBannerURL() {
		return bannerURL;
	}

	public void setBannerURL(String bannerURL) {
		this.bannerURL = bannerURL;
	}

	@URL
	@NotBlank
	public String getInfoPageLink() {
		return infoPageLink;
	}

	public void setInfoPageLink(String infoPageLink) {
		this.infoPageLink = infoPageLink;
	}

	@Valid
	@ManyToOne(optional=false)
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	// RelationShips

	private Trip trip;
	private Sponsor sponsor;

	@Valid
	@ManyToOne(optional=true)
	@NotNull
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Valid
	@ManyToOne(optional=false)
	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

}
