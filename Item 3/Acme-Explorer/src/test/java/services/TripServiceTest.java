package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
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
	@Autowired
	private ConfigurationService configurationService;
	
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
		trip.setStartDate(Date.valueOf("2018-07-07"));
		trip.setEndDate(Date.valueOf("2018-08-08"));
		trip.setLegalText(legalText);
		trip.setRanger(ranger);
		trip.setValues(values);
		trip.setStages(stages);
		
		Assert.isTrue(trip.getStatus().equals("ACCEPTED"));
		
		
		tSaved = tripService.save(trip);
		
		
		tripId = tSaved.getId();
		tFound = tripService.findOne(tripId);
		Assert.isTrue(tSaved.equals(tFound));
		Assert.notNull(tFound);
		
		//Probamos delete
		tripService.delete(tSaved);
		tFound = tripService.findOne(tripId);
		Assert.isTrue(tFound==null);
		
	}

	
//	trip = tripService.create();
//	stage = stageService.findOne(2513);
//	legalText = legalTextService.findOne(2486);
//	ranger = rangerService.findOne(2498);
//	category = categoryService.findOne(2418);
//	values = valueService.findOne(2540);
	
	@Test
	public void testCancelTripNotStarted(){
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
		requirements = "req2";
		values = valueService.findAll();
		
		
		Assert.notNull(trip);
		
		trip.setCategory(category);
		trip.setTitle("Titulo");
		trip.setDescription("descripcion");
		trip.setPrice(1500.00);
		trip.setExplorerRequirements(requirements);
		trip.setPublicationDate(Date.valueOf("2016-11-11"));
		trip.setStartDate(Date.valueOf("2018-07-07"));
		trip.setEndDate(Date.valueOf("2018-08-08"));
		trip.setLegalText(legalText);
		trip.setRanger(ranger);
		trip.setValues(values);
		trip.setStages(stages);
		
		tSaved = tripService.save(trip);
		
		
		tripId = tSaved.getId();
		tFound = tripService.findOne(tripId);

		
//		Trip trip = tripService.findOne(2535);
		Assert.isTrue(tFound.getStatus().equals("ACTIVE"));
		tripService.cancelTripNotStarted(tFound);
		Assert.isTrue(tFound.getStatus().equals("CANCELLED"));
		super.authenticate(null);
	}
	
	@Test
	public void testCancelTripAfterPublication(){
		super.authenticate("manager1");
		//Trip trip = tripService.findOne(2534);
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
		requirements = "req3";
		values = valueService.findAll();
		
		
		Assert.notNull(trip);
		
		trip.setCategory(category);
		trip.setTitle("Titulo");
		trip.setDescription("descripcion");
		trip.setPrice(1500.00);
		trip.setExplorerRequirements(requirements);
		trip.setPublicationDate(Date.valueOf("2016-11-11"));
		trip.setStartDate(Date.valueOf("2018-07-07"));
		trip.setEndDate(Date.valueOf("2018-08-08"));
		trip.setLegalText(legalText);
		trip.setRanger(ranger);
		trip.setValues(values);
		trip.setStages(stages);
		
		tSaved = tripService.save(trip);
		
		
		tripId = tSaved.getId();
		tFound = tripService.findOne(tripId);
		
		
		Assert.isTrue(tFound.getStatus().equals("ACTIVE"));
		String cancelReason = "Reason";
		tripService.cancelTripAferPublication(tFound, cancelReason);
		Assert.isTrue(tFound.getStatus().equals("CANCELLED"));
		Assert.isTrue(tFound.getCancelReason().contains(cancelReason));
		super.authenticate(null);
	}
	
	
//	@Test
//	public void TestGetTripPrice(){
//		super.authenticate("manager1");
//		//Trip trip = tripService.findOne(2534);
//		
//		Trip trip;
//		List<Stage> allStages;
//		Stage stage1;
//		Stage stage2;
//		Collection<Stage> stages=new ArrayList<Stage>();
//		List<LegalText> legalTexts;
//		LegalText legalText;
//		List<Ranger> rangers;
//		Ranger ranger;
//		List<Category> categories;
//		Category category;
//		Collection<String> requirements;
//		Collection<Value> values;
//		Trip tFound, tSaved;
//		Integer tripId;
//		
//		trip = tripService.create();
//		allStages = (List<Stage>) stageService.findAll();
//		stage1 = allStages.get(0);
//		stage1.setPrice(200.0);
//		stage2 = allStages.get(1);
//		stage2.setPrice(500.0);
//		stages.add(stage1);
//		stages.add(stage2);
//		legalTexts = ((List<LegalText>) legalTextService.findAll());
//		legalText = legalTexts.get(0);
//		rangers = (List<Ranger>) rangerService.findAll();
//		ranger = rangers.get(0);
//		categories = (List<Category>) categoryService.findAll();
//		category = categories.get(0);
//		requirements = new ArrayList<>();
//		values = valueService.findAll();
//		
//		requirements.add("Requisito 1");
//		
//		Assert.notNull(trip);
//		
//		trip.setCategory(category);
//		trip.setTitle("Titulo");
//		trip.setDescription("descripcion");
//	//	trip.setPrice(1500.00);
//		trip.setExplorerRequirements(requirements);
//		trip.setPublicationDate(Date.valueOf("2016-11-11"));
//		trip.setStartDate(Date.valueOf("2018-07-07"));
//		trip.setEndDate(Date.valueOf("2018-08-08"));
//		trip.setLegalText(legalText);
//		trip.setRanger(ranger);
//		trip.setValues(values);
//		trip.setStages(stages);
//		
//		tSaved = tripService.save(trip);
//		
//		tripId = tSaved.getId();
//		tFound = tripService.findOne(tripId);
//		
//		System.out.println(trip.getPrice());
//		System.out.println(tripService.getTripPrice(tFound));
//		System.out.println(configService.getTax());
//		System.out.println(trip.getPrice()*configService.getTax());
//		
//		Assert.isTrue(tripService.getTripPrice(tFound) == trip.getPrice()*configService.getTax());
//		super.authenticate(null);
//		
//	}
	
//	@Test
//	public void testTripsByKeyWord(){
//		Trip trip;
//		List<Stage> allStages;
//		Stage stage1;
//		Stage stage2;
//		Collection<Stage> stages=new ArrayList<Stage>();
//		List<LegalText> legalTexts;
//		LegalText legalText;
//		List<Ranger> rangers;
//		Ranger ranger;
//		List<Category> categories;
//		Category category;
//		Collection<String> requirements;
//		Collection<Value> values;
//		
//		trip = tripService.create();
//		allStages = (List<Stage>) stageService.findAll();
//		stage1 = allStages.get(0);
//		stage2 = allStages.get(1);
//		stages.add(stage1);
//		stages.add(stage2);
//		legalTexts = ((List<LegalText>) legalTextService.findAll());
//		legalText = legalTexts.get(0);
//		rangers = (List<Ranger>) rangerService.findAll();
//		ranger = rangers.get(0);
//		categories = (List<Category>) categoryService.findAll();
//		category = categories.get(0);
//		requirements = new ArrayList<>();
//		values = valueService.findAll();
//		
//		requirements.add("Requisito 1");
//		
//		Assert.notNull(trip);
//		
//		trip.setCategory(category);
//		trip.setTitle("Titulo");
//		trip.setDescription("descripcion");
//		trip.setPrice(1500.00);
//		trip.setExplorerRequirements(requirements);
//		trip.setPublicationDate(Date.valueOf("2016-11-11"));
//		trip.setStartDate(Date.valueOf("2018-07-07"));
//		trip.setEndDate(Date.valueOf("2018-08-08"));
//		trip.setLegalText(legalText);
//		trip.setRanger(ranger);
//		trip.setValues(values);
//		trip.setStages(stages);
//		
//		tripService.save(trip);
//		
//		Collection<Trip> result = tripService.tripsByKeyWord("descripcion");
//		
//		Assert.isTrue(result.contains(trip));
//	}

	@Test
	public void searchCriteria(){
		String word = "trip";
		Double minPrice = 0.;
		Double maxPrice = 99999.0;
		Date startDate = Date.valueOf("2018-07-07");
		Date finishDate = Date.valueOf("2018-08-08");
		
		//Collection<Trip> trips = tripService.findTripsBySearchCriteria(minPrice,maxPrice,startDate,finishDate,word);
		Collection<Trip> trips = tripService.findTripsBySearchCriteria(null,3000.,null,null,null);
		Assert.notNull(trips);
//		System.out.println(trips);
	}
	
	@Test
	public void getEndedTrips(){
		Collection<Trip> endedTrips;
		
		endedTrips = tripService.getEndedTrips();
	}

	@Test
	public void paginatedTripRepository(){
		Collection<Trip> trips;
		
		trips = tripService.paginatedTripsSearch(0,5).getContent();
		Assert.isTrue(trips.size()==1);
		
		Trip t = (Trip) trips.toArray()[0];
		
		System.out.println("The cheaper trip is " +t.getTitle() +", whose price is " + t.getPrice());
	}
	
	@Test
	public void searchAllTripsByCategory(){
		Collection<Trip> trips;
		Collection<Trip> trips2 = new ArrayList<>();
		
//		trips = tripService.showAllTripsByCategory(3348, trips2);
	}

}
