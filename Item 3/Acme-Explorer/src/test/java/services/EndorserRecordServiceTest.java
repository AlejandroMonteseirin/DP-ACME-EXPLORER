
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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	@Autowired
	private EndorserRecordService endorserRecordService;

	@Test
	public void createSaveDelete() {
		authenticate("ranger2");
		
		EndorserRecord edRecord, edSaved;
		Collection<EndorserRecord> eBefore, eAfter;
		
		edRecord = endorserRecordService.create();
		Assert.notNull(edRecord);
		
		//Probamos save
		edRecord.setEmail("email@email.es");
		edRecord.setFullName("name");
		edRecord.setPhoneNumber("131232");
		edSaved = endorserRecordService.save(edRecord);
		
		eBefore = endorserRecordService.findAll();
		Assert.isTrue(eBefore.contains(edSaved));
		
		//Probamos delete
		endorserRecordService.delete(edSaved);
		
		eAfter = endorserRecordService.findAll();
		
		Assert.isTrue(!eAfter.contains(edSaved));
	}
}
