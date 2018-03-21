
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ValueRepository;
import domain.Tag;
import domain.Trip;
import domain.Value;

@Service
@Transactional
public class ValueService {

	// Managed repository
	@Autowired
	private ValueRepository	valueRepository;
	@Autowired
	private TagService	tagService;
	@Autowired
	private TripService	tripService;


	// Constructor
	public ValueService() {
		super();
	}

	// Simple CRUD methods
	public Value create(int tagId) {
		Value v;
		Tag t;
		
		v = new Value();
		t = tagService.findOne(tagId);
		
		v.setTag(t);
		
		return v;
	}

	public Collection<Value> findAll() {
		Collection<Value> result;
		result = this.valueRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Value findOne(final int valueId) {
		Value result;
		result = this.valueRepository.findOne(valueId);
		return result;
	}

	public Value save(final Value value) {
		Assert.notNull(value);
		final Value result;
		result = this.valueRepository.save(value);
		
		return result;
	}

	public void delete(final Value value) {
		Assert.notNull(value);
		Assert.isTrue(value.getId() != 0);
		
		Collection<Trip> trip = tripService.getTripsByValue(value.getId());
		Collection<Value> tripValues;
		
		for (Trip t : trip) {
			tripValues = t.getValues();
			tripValues.remove(value);
			t.setValues(tripValues);
		}
		
		this.valueRepository.delete(value);
		
		

	}
	
	//Other business methods
	
	public Collection<Value> getValuesByTagId(int tagId){
		return valueRepository.getValuesByTagId(tagId);
	}

}
