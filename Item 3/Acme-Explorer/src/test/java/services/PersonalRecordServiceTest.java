
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
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	@Autowired
	private PersonalRecordService personalRecordService;

	@Test
	public void createSaveDelete() {
		authenticate("ranger2");
		
		PersonalRecord edRecord, edSaved;
		Collection<PersonalRecord> eBefore, eAfter;
		
		edRecord = personalRecordService.create();
		Assert.notNull(edRecord);
		
		//Probamos save
		edRecord.setEmail("email@email.es");
		edRecord.setFullName("name");
		edRecord.setPhoneNumber("131232");
		edRecord.setPhotoUrl("http://www.foto.jpg");
		edRecord.setLinkedInProfileUrl("http://www.foto.jpg");
		edSaved = personalRecordService.save(edRecord);
		
		eBefore = personalRecordService.findAll();
		Assert.isTrue(eBefore.contains(edSaved));
		
		//Probamos delete
		personalRecordService.delete(edSaved);
		
		eAfter = personalRecordService.findAll();
		
		Assert.isTrue(!eAfter.contains(edSaved));
	}

}
