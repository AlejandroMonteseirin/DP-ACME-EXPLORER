
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	@Test
	public void createSaveDelete() {
		authenticate("ranger2");
		
		MiscellaneousRecord edRecord, edSaved;
		Collection<MiscellaneousRecord> eBefore, eAfter;
		
		edRecord = miscellaneousRecordService.create();
		Assert.notNull(edRecord);
		
		//Probamos save
		edRecord.setTitle("title");
		edSaved = miscellaneousRecordService.save(edRecord);
		
		eBefore = miscellaneousRecordService.findAll();
		Assert.isTrue(eBefore.contains(edSaved));
		
		//Probamos delete
		miscellaneousRecordService.delete(edSaved);
		
		eAfter = miscellaneousRecordService.findAll();
		
		Assert.isTrue(!eAfter.contains(edSaved));
	}

}
