package services;

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
import domain.Application;
import domain.Folder;
import domain.Manager;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ManagerServiceTest extends AbstractTest {

	@Autowired
	private ManagerService managerService;
	 @Autowired
	 private FolderService folderService;
	@Autowired
	private ApplicationService applicationService;
//	@Autowired
//	private TripService tripService;
	
	@Test
	public void testCreateManager(){
		Manager m = null;
		m = managerService.create();
		Assert.notNull(m);
	}
	
	@Test
	public void testSaveManager(){
		Manager m,saved;
		Collection<Manager> managers;
		m = managerService.create();
		m.setName("Juanjo");
		m.setSurname("Peña");
		saved = managerService.save(m);	
		managers = managerService.findAll();
		Assert.isTrue(managers.contains(saved));
		
	}
	
	@Test
	public void testDeleteManager(){
		Manager m,saved;
		Collection<Manager> managers;
		m = managerService.create();
		m.setName("Arturo");
		m.setSurname("Sevilla");
		saved = managerService.save(m);
		managerService.delete(saved);
		managers = managerService.findAll();
		Assert.isTrue(!managers.contains(saved));
	}



	@Test
	public void testGetSuspiciousManagers() {

		Collection<Manager> suspiciousManagers = managerService
				.getSuspiciousManagers();
		for (Manager m : suspiciousManagers) {
			Assert.isTrue(m.getSuspicious());
		}

	}

	@Test
	public void testGetSuspiciousManagersRatio() {

		Assert.isTrue(managerService.getSuspiciousManagersRatio() == 1.0);

	}
//
	@Test
	public void testGetManagerFromApplicationId() {

		Collection<Application> applications = applicationService.findAll();
		Collection<Manager> managers = managerService.findAll();
		List<Application> app = new ArrayList<Application>(applications);
		Application apl = applicationService.findOne(app.get(0).getId());
		Trip trip = apl.getTrip();
		Manager save = new Manager();
		for (Manager manager : managers) {
			if (manager.getTrips().contains(trip)) {
				save = manager;

			}

		}

		//System.out.println(save.getName() + save.getEmail());
		Assert.isTrue(save != null);

	}

}
