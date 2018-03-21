package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.Explorer;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest{
	
	// Service under test ---------------------------------
	@Autowired
	private ApplicationService	applicationService;
	@Autowired 
	private TripService tripService;
	@Autowired 
	private ExplorerService explorerService;

	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveDelete() {
		authenticate("explorer1");

		Application app;
		List<Trip> trips = new ArrayList<>();
		Trip trip;
		Application appSaved;
		Collection<Application> appsBefore;
		Collection<Application> appsAfter;
		
		trips = (List<Trip>) tripService.findAll();
		trip = trips.get(0);
		app = applicationService.create();

		Assert.notNull(app);
		Assert.notNull(app.getCreationMoment());
		Assert.isTrue(app.getStatus().equals("PENDING"));
		
		//Probamos save
		
		app.setCancelReason("");
		app.setComment("comment");
		
		appSaved = applicationService.save(app);
		Assert.notNull(appSaved);
		
		//Probamos delete
		appsBefore = applicationService.findAll();
		Assert.isTrue(appsBefore.contains(appSaved));
		
		applicationService.delete(appSaved);
		appsAfter = applicationService.findAll();
		Assert.isTrue(!appsAfter.contains(appSaved));
		
	}	
	
	@Test
	public void cancelApplicationAccepted(){
		super.authenticate("explorer1");
		
		Application app;
		List<Trip> trips = new ArrayList<>();
		Trip trip;
		Application appSaved;
		
		trips = (List<Trip>) tripService.findAll();
		trip = trips.get(0);
		app = applicationService.create();

		app.setStatus("ACCEPTED");
		app.setCancelReason("");
		app.setComment("comment");
		app.getTrip().setStartDate(new Date(System.currentTimeMillis()+10000));
		
		appSaved = applicationService.save(app);
		
		applicationService.cancelApplication(appSaved);
		Assert.isTrue(appSaved.getStatus().equals("CANCELLED"));
		super.authenticate(null);
		
	}
	
}