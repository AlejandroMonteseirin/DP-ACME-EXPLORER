
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
import domain.Manager;
import domain.Note;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	@Autowired
	private NoteService		noteService;
	@Autowired
	private TripService		tripService;
	@Autowired
	private AuditorService	auditorService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void createSaveAndDelete() {
		this.authenticate("auditor1");

		Note n, nSaved;
		Trip trips;
		final Collection<Note> notesBefore = new ArrayList<>();

		trips = (Trip) this.tripService.findAll().toArray()[0];
		n = this.noteService.create();
		Assert.notNull(n);

		//Probamos save
		n.setRemark("holis personis");
		n.setTrip(trips);

		nSaved = this.noteService.save(n);
		System.out.println(nSaved);
		//notesBefore = this.noteService.findAll();
		//Assert.isTrue(notesBefore.contains(nSaved));
		
		super.authenticate(null);

	}
	
	@Test
	public void notesByManagerTrips(){
		authenticate("manager2");
		Manager m;
		Collection<Note> notes;
		
		m = (Manager) actorService.findByPrincipal();
		notes = noteService.getNotesToManagerTrips(m.getId());
		
	}

}
