
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.LegalText;
import domain.Trip;

@Service
@Transactional
public class LegalTextService {

	//TODO   C.14.6 Tabla con el número de veces que cada LegalText ha sido referenciado(dashboard) -->Admin //falta en service ¿qué devuelve una consulta de dos columnas?
	// sólo legalText en finalMode pueden ser referenciados por Trip

	// Managed repository
	@Autowired
	private LegalTextRepository	legalTextRepository;
	@Autowired
	private TripService			tripService;


	// Constructor
	public LegalTextService() {
		super();
	}

	// Simple CRUD methods
	public LegalText create() {

		LegalText l;
		Collection<Trip> trips;

		l = new LegalText();
		trips = new ArrayList<>();
		final Date moment = new Date(System.currentTimeMillis() - 1);

		l.setMoment(moment);
		l.setTrips(trips);

		return l;
	}

	public Collection<LegalText> findAll() {
		final Collection<LegalText> result = this.legalTextRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public LegalText findOne(final int legalTextId) {
		final LegalText result = this.legalTextRepository.findOne(legalTextId);
		return result;
	}

	public LegalText save(final LegalText legalText) {
		Assert.notNull(legalText);
		final Date moment = new Date(System.currentTimeMillis() - 1);
		legalText.setMoment(moment);

		final LegalText result = this.legalTextRepository.save(legalText);

		return result;
	}

	public void delete(final LegalText legalText) {
		Assert.notNull(legalText);
		//Assert.isTrue(!legalText.getSavedMode().equals("DRAFT MODE"), "message.error.finalMode");
		this.legalTextRepository.delete(legalText);

	}

	//Other business methods

	public Map<LegalText, Integer> getLegalTextReferenceCount() {
		final Map<LegalText, Integer> res = new HashMap<LegalText, Integer>();
		for (final LegalText l : this.legalTextRepository.findAll())
			res.put(l, l.getTrips().size());
		return res;
	}

	public Collection<LegalText> getFinalModeLegalTexts() {
		return this.legalTextRepository.getFinalModeLegalTexts();
	}

}
