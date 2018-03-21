
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Story;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StoryServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private StoryService	storyService;

	@Autowired
	private TripService		tripService;


	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveAndDelete() {
//		
//		Trip trip;
//		trip = (Trip) tripService.findAll().toArray()[0];
//		
//		authenticate("explorer1");
//		final Story story = this.storyService.create(trip);
//		Assert.notNull(story, "fallo en el create,es null");
//		story.setTitle("El viaje a cuenca");
//		story.setText("El viaje a cuenca estuvo fabuloso,fuimos en una camioneta y vimos toda cuenca, 10/10 muy recomendado");
//		final Collection<String> attachmentURLs = new ArrayList<>();
//		attachmentURLs.add("https://www.cuenca.es");
//		attachmentURLs.add("https://www.IglesiaDeCuenca.es");
//		attachmentURLs.add("https://www.ProvinciaDeCuenca.com");
//		story.setAttachmentURLs(attachmentURLs);
//		story.setTrip(trip);
//
//		final Story storySaved = this.storyService.save(story);
//		Assert.notNull(storySaved, "fallo en el save,es null");
//		
//		Integer storyId = storySaved.getId();
//		storyService.delete(storySaved);
//		Assert.isTrue(storyService.findOne(storyId)==null);
//
//		super.authenticate(null);

	}

}
