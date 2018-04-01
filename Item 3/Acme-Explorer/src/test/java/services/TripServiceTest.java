package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import utilities.AbstractTest;
import domain.Category;
import domain.LegalText;
import domain.Ranger;
import domain.Stage;
import domain.Trip;
import domain.Value;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
public class TripServiceTest extends AbstractTest{
	
	// SERVICES UNDER TEST --------------
	@Autowired
	private TripService tripService;
	
	@Autowired
	private StageService stageService;
	@Autowired
	private LegalTextService legalTextService;
	@Autowired
	private RangerService rangerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ValueService valueService;
	
	// TESTS ------------------------
	@Test
	public void testCreateTrip(){
		
		Trip t;
		
		t = tripService.create();
		Assert.notNull(t);
	}
	
	@Test
	public void testSaveDeleteTrip(){
		
		super.authenticate("manager1");
		Trip trip;
		List<Stage> allStages;
		Stage stage1;
		Stage stage2;
		Collection<Stage> stages=new ArrayList<Stage>();
		List<LegalText> legalTexts;
		LegalText legalText;
		List<Ranger> rangers;
		Ranger ranger;
		List<Category> categories;
		Category category;
		String requirements;
		Collection<Value> values;
		Trip tFound, tSaved;
		Integer tripId;
		
		trip = tripService.create();
		allStages = (List<Stage>) stageService.findAll();
		stage1 = allStages.get(0);
		stage2 = allStages.get(1);
		stages.add(stage1);
		stages.add(stage2);
		legalTexts = ((List<LegalText>) legalTextService.findAll());
		legalText = legalTexts.get(0);
		rangers = (List<Ranger>) rangerService.findAll();
		ranger = rangers.get(0);
		categories = (List<Category>) categoryService.findAll();
		category = categories.get(0);
		requirements = "req1";
		values = valueService.findAll();
		
		
		Assert.notNull(trip);
		
		trip.setCategory(category);
		trip.setTitle("Titulo");
		trip.setDescription("descripcion");
		trip.setPrice(1500.00);
		trip.setExplorerRequirements(requirements);
		trip.setPublicationDate(Date.valueOf("2018-11-11"));
		trip.setStartDate(Date.valueOf("2019-07-07"));
		trip.setEndDate(Date.valueOf("2020-08-08"));
		trip.setLegalText(legalText);
		trip.setRanger(ranger);
		trip.setValues(values);
		trip.setStages(stages);
		
		Assert.isTrue(trip.getStatus().equals("ACTIVE"));
		
		
		tSaved = tripService.save(trip);
		
		
		tripId = tSaved.getId();
		tFound = tripService.findOne(tripId);
		Assert.isTrue(tSaved.equals(tFound));
		Assert.notNull(tFound);
		
		//Cancelamos
		
		String cancelReason = "Reason";
		tFound.setPublicationDate(Date.valueOf("2016-11-11"));
		tripService.cancelTripAferPublication(tFound, cancelReason);
		Assert.isTrue(tFound.getStatus().equals("CANCELLED"));
		Assert.isTrue(tFound.getCancelReason().contains(cancelReason));
		
		//Probamos delete
		tFound.setPublicationDate(Date.valueOf("2018-11-11"));
		tripService.delete(tSaved);
		tFound = tripService.findOne(tripId);
		Assert.isTrue(tFound==null);
		
	}

	
	
	
	
	

	@Test
	public void searchCriteria(){
		Collection<Trip> trips = tripService.findTripsBySearchCriteria(null,3000.,null,null,null);
		Assert.notNull(trips);
	}
	
	@Test
	public void getEndedTrips(){
		Collection<Trip> endedTrips;
		
		endedTrips = tripService.getEndedTrips();
		Assert.notNull(endedTrips);
	}

	@Test
	public void paginatedTripRepository(){
		Collection<Trip> trips;
		
		trips = tripService.paginatedTripsSearch(1,5).getContent();
		Assert.isTrue(trips.size()>0);
		
		Trip t = (Trip) trips.toArray()[0];
		
		System.out.println("The cheaper trip is " +t.getTitle() +", whose price is " + t.getPrice());
	}
	
	@Test
	public void searchAllTripsByCategory(){
		Collection<Trip> trips;
		List<Category> categories = (List<Category>) categoryService.findAll();
		trips = tripService.getTripsByCategory(categories.get(0).getId());
		Assert.notNull(trips);
		
	}

}
