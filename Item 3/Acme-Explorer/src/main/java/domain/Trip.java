
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
//@Indexed //A+ Full-Text Search
public class Trip extends DomainEntity {

	public Trip() {
		super();
	}


	// Attributes -------------------

	private String	ticker;
	private String	title;
	private String	description;
	private double	price;
	private String	explorerRequirements;
	private Date	publicationDate;
	private Date	startDate;
	private Date	endDate;
	private String	status;
	private String	cancelReason;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "(^\\d\\d[0-1]\\d[0-3]\\d[-]\\w{4})$")
	@Field
	//A+ Full-Text Search
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	@Field
	//A+ Full-Text Search
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@Field
	//A+ Full-Text Search
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotEmpty
	public String getExplorerRequirements() {
		return this.explorerRequirements;
	}

	public void setExplorerRequirements(final String requirements) {
		this.explorerRequirements = requirements;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotNull
	@Pattern(regexp = "(^CANCELLED|ACTIVE$)")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(final String cancelReason) {
		this.cancelReason = cancelReason;
	}


	// Relationships

	private Collection<Stage>		stages;
	private Collection<Sponsorship>	sponsorships;
	private Collection<Audit>		audits;
	private Collection<Note>		notes;
	private Collection<Application>	applications;
	private Ranger					ranger;
	private Category				category;
	private LegalText				legalText;
	private Collection<Value>		values;


	@ManyToMany
	public Collection<Value> getValues() {
		return this.values;
	}

	public void setValues(final Collection<Value> values) {
		this.values = values;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
	public Collection<Stage> getStages() {
		return this.stages;
	}

	public void setStages(final Collection<Stage> stages) {
		this.stages = stages;
	}

	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@ManyToOne(optional = true)
	@NotNull
	public Ranger getRanger() {
		return this.ranger;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Valid
	@ManyToOne(optional=true, cascade = CascadeType.MERGE)
	public LegalText getLegalText() {
		return this.legalText;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}

	//	@Override
	//	public String toString() {
	//		return title + " " ;
	//	}

}
