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
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	@Autowired
	private EducationRecordService educationRecordService;

	@Test
	public void createSaveDelete() {
		authenticate("ranger2");
		
		EducationRecord edRecord, edSaved;
		Collection<EducationRecord> eBefore, eAfter;
		
		edRecord = educationRecordService.create();
		Assert.notNull(edRecord);
		
		//Probamos save
		edRecord.setDiplomaTitle("diploma");
		edRecord.setInstitutionName("name");
		edRecord.setStartDate(Date.valueOf("2014-08-08"));
		edSaved = educationRecordService.save(edRecord);
		
		eBefore = educationRecordService.findAll();
		Assert.isTrue(eBefore.contains(edSaved));
		
		//Probamos delete
		educationRecordService.delete(edSaved);
		
		eAfter = educationRecordService.findAll();
		
		Assert.isTrue(!eAfter.contains(edSaved));
	}

}
