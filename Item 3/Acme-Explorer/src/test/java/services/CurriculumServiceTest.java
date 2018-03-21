package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.PersonalRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private TripService tripService;

	@Autowired
	private PersonalRecordService personalRecordService;

	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveAndDelete() {
		authenticate("ranger1");
		Curriculum curriculum, curriculumSaved;
		PersonalRecord pR;
		Collection<Curriculum> cBefore, cAfter;
		
		curriculum = curriculumService.create();
		Assert.notNull(curriculum);

		
		//Probamos save
		pR = personalRecordService.create();
		pR.setEmail("email@email.es");
		pR.setFullName("name");
		pR.setLinkedInProfileUrl("https://www.asdas.es");
		pR.setPhoneNumber("1232");
		pR.setPhotoUrl("https://www.asdas.es");
		personalRecordService.save(pR);
		
		curriculum.setPersonalRecord(pR);
		curriculum.setTicker(tripService.getTicker());
		
		curriculumSaved = curriculumService.save(curriculum);
		Assert.notNull(curriculumSaved);
		
		cBefore = curriculumService.findAll();
		Assert.isTrue(cBefore.contains(curriculumSaved));
		
		//Probamos delete
		curriculumService.delete(curriculumSaved);
		cAfter = curriculumService.findAll();
		
		Assert.isTrue(!cAfter.contains(curriculumSaved));
		
	}

}
