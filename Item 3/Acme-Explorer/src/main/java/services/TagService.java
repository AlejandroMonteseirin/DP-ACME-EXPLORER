
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Tag;
import domain.Value;

@Service
@Transactional
public class TagService {

	// Managed repository
	@Autowired
	private TagRepository	tagRepository;
	
	// Supporting services
	@Autowired
	private ValueService	valueService;

	// Constructor
	public TagService() {
		super();
	}

	// Simple CRUD methods
	public Tag create() {
		Tag t;
		
		t = new Tag();
		
		return t;
	}

	public Collection<Tag> findAll() {
		Assert.notNull(this.tagRepository.findAll());
		return this.tagRepository.findAll();
	}

	public Tag findOne(final int tagId) {
		return this.tagRepository.findOne(tagId);
	}

	public Tag save(final Tag tag) {

		return this.tagRepository.save(tag);
	}

	public void delete(final Tag tag) {
		
		Collection<Value> values = valueService.getValuesByTagId(tag.getId());
		
		for(Value v : values){
			valueService.delete(v);
		}
		
		this.tagRepository.delete(tag);
	}

}
