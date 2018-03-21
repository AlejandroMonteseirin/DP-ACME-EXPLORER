package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity{
	
	private String bannerURL;
	private String welcomeMessageEN;
	private String welcomeMessageES;
	private Collection<String> spamWords;
	private double VATTax;
	private String countryCode;
	private double finderCached;
	private int finderReturn;
	
	@URL
	public String getBannerURL() {
		return bannerURL;
	}
	
	public void setBannerURL(String bannerURL) {
		this.bannerURL = bannerURL;
	}
	
	
	@NotBlank
	public String getWelcomeMessageEN() {
		return welcomeMessageEN;
	}

	public void setWelcomeMessageEN(String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}

	@NotBlank
	public String getWelcomeMessageES() {
		return welcomeMessageES;
	}

	public void setWelcomeMessageES(String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getSpamWords() {
		return spamWords;
	}
	
	public void setSpamWords(Collection<String> spamWords) {
		this.spamWords = spamWords;
	}
	
	
	public double getVATTax() {
		return VATTax;
	}
	
	public void setVATTax(double vATTax) {
		VATTax = vATTax;
	}
	
	@NotBlank
	@Min(1)
	@Max(999)
	public String getCountryCode() {
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	@Min(1)
	@Max(24)
	public double getFinderCached() {
		return finderCached;
	}
	
	public void setFinderCached(double finderCached) {
		this.finderCached = finderCached;
	}
	
	@Max(100)
	public int getFinderReturn() {
		return finderReturn;
	}
	
	public void setFinderReturn(int finderReturn) {
		this.finderReturn = finderReturn;
	}
	
	
	

}
