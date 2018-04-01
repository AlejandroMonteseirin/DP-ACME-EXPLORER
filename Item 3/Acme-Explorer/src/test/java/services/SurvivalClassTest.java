package services;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Location;
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SurvivalClassTest extends AbstractTest{
	
	//Service under test
	@Autowired
	private SurvivalClassService survivalClassService;
		
	@Autowired
	private TripService tripService;
	
		@Test
		public void createSaveAndDelete(){
			authenticate("manager1");
			
			SurvivalClass c;
			SurvivalClass cSaved;
			Location l = new Location();
			l.setName("name");
			l.setCoordinates("0,0");
			c = survivalClassService.create();
			List<Trip> trips = (List<Trip>) tripService.findAll();
			Trip trip=trips.get(0);
			Assert.notNull(c);
			
			c.setDescription("description");
			c.setLocation(l);
			c.setOrganizationDate(Date.valueOf("2019-07-30"));
			c.setTitle("title");
			c.setTrip(trip);
		
			//Probamos save
			cSaved = survivalClassService.save(c);
			Assert.notNull(cSaved);
			
			Integer idCS = cSaved.getId();
			Assert.notNull(survivalClassService.findOne(idCS));
			
			//Probamos delete
			survivalClassService.delete(cSaved);
			Assert.isTrue(survivalClassService.findOne(idCS)==null);
		}


}
