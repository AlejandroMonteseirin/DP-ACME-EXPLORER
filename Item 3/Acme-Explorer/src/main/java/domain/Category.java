package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	public Category() {
		super();
	}

	//Attributes
	private String name;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Relationships
	private Category parentCategory;
	private Collection<Category> childCategories;
	
	@Valid
	@ManyToOne
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Valid
	@OneToMany(mappedBy="parentCategory")
	public Collection<Category> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(Collection<Category> childCategories) {
		this.childCategories = childCategories;
	}
	
	
	

}
