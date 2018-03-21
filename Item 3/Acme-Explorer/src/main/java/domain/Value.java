
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Value extends DomainEntity {

	private String	content;
	private Tag		tag;


	public Value() {
		super();
	}

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Tag getTag() {
		return this.tag;
	}

	public void setTag(final Tag tag) {
		this.tag = tag;
	}

	@NotBlank
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
