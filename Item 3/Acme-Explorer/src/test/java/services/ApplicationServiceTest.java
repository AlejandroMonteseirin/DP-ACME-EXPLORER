package services;

import java.sql.Date;
import java.util.ArrayList;
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

	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveDelete() {
		authenticate("explorer1");

		Application app;
		Application appSaved;
		
		app = applicationService.create();

		Assert.notNull(app);
		Assert.notNull(app.getCreationMoment());
		Assert.isTrue(app.getStatus().equals("PENDING"));
		
		appSaved = applicationService.save(app);
		Assert.notNull(appSaved);
		applicationService.delete(appSaved);
		
	}	
	
	@Test
	public void cancelApplicationAccepted(){
		super.authenticate("explorer1");
		
		Application app;
		List<Trip> trips = new ArrayList<>();
		Application appSaved;
		
		trips = (List<Trip>) tripService.findAll();
		app = applicationService.create();

		app.setStatus("ACCEPTED");
		app.setCancelReason("");
		app.setComment("comment");
		
		app.setTrip(trips.get(0));
//		app.getTrip().setStartDate(new Date(System.currentTimeMillis()+10000));
		
		appSaved = applicationService.save(app);
		
		appSaved.getTrip().setStartDate(Date.valueOf("2020-02-02"));
		applicationService.cancelApplication(appSaved);
		Assert.isTrue(appSaved.getStatus().equals("CANCELLED"));
		super.authenticate(null);
		
	}
	
}