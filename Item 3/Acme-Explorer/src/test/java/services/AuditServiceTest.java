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
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class AuditServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private AuditService auditService;
	@Autowired
	private TripService tripService;
	@Autowired
	private ActorService actorService;

	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveAndDelete() {
		authenticate("auditor1");

		List<Trip> tripBD;
		Trip trip;
		Audit audit, aSaved;
		Collection<Audit> aBefore, aAfter;
		
		tripBD = new ArrayList<>(tripService.findAll());
		trip = tripBD.get(0);
		audit = auditService.create();
		
		Assert.notNull(audit);

		//Probamos save
		audit.setDescription("Audicion de paco el cantador intento numero 23");
		audit.setTitle("paco el cantador grabacion");
		audit.getAttachmentURLs().add("paquito.es");
		audit.getAttachmentURLs().add("youtube.es");
		audit.setTrip(trip);
		audit.setAuditor((Auditor) actorService.findByPrincipal());
		audit.setSavedMode("DRAFT MODE");

		aSaved = auditService.save(audit);
		Assert.notNull(aSaved);
		
		aBefore = auditService.findAll();
		Assert.isTrue(aBefore.contains(aSaved));
		
		//Comprobamos delete
		auditService.delete(aSaved);
		
		aAfter = auditService.findAll();
		
		Assert.isTrue(!aAfter.contains(aSaved));
	}

}
