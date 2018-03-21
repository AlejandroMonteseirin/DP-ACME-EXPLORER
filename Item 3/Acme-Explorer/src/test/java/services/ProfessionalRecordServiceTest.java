
package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	@Autowired
	private ProfessionalRecordService professionalRecordService;

	@Test
	public void createSaveDelete() {
		authenticate("ranger2");
		
		ProfessionalRecord edRecord, edSaved;
		Collection<ProfessionalRecord> eBefore, eAfter;
		
		edRecord = professionalRecordService.create();
		Assert.notNull(edRecord);
		
		//Probamos save
		edRecord.setPlayedRole("asda");
		edRecord.setCompanyName("name");
		edRecord.setStartDate(Date.valueOf("2016-06-06"));
		edSaved = professionalRecordService.save(edRecord);
		
		eBefore = professionalRecordService.findAll();
		Assert.isTrue(eBefore.contains(edSaved));
		
		//Probamos delete
		professionalRecordService.delete(edSaved);
		
		eAfter = professionalRecordService.findAll();
		
		Assert.isTrue(!eAfter.contains(edSaved));
	}

}
