package services;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Stage;
import domain.Trip;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class StageServiceTest extends AbstractTest{
	
	// SERVICE TESTED
	@Autowired
	private StageService stageService;
	@Autowired
	private TripService tripService;
	
	// TESTS
	
	@Test
	public void createSaveAndDeleteTest(){
		authenticate("manager1");
		List<Trip> trips = (List<Trip>) tripService.findAll();
		trips.get(1).setPublicationDate(Date.valueOf("2020-02-02"));
		trips.get(1).setStartDate(Date.valueOf("2021-02-02"));
		trips.get(1).setEndDate(Date.valueOf("2022-02-02"));
		Stage stage = stageService.create(trips.get(1).getId());
		Assert.notNull(stage);
		
		Collection<Stage> stagesBefore = stageService.findAll();
		
		stage.setTitle("Test");
		stage.setDescription("descripcion");
		stage.setPrice(250.0);
		
		Stage stageSaved = stageService.save(stage);
		Assert.notNull(stageSaved);
		Collection<Stage> stagesAfter = stageService.findAll();
		
		Assert.isTrue(!stagesBefore.contains(stageSaved) && stagesAfter.contains(stageSaved));
		
		stageService.delete(stageSaved);
		Assert.isTrue(stageService.findOne(stageSaved.getId())==null);
	}
	
//	@Test
//	public void deleteTest(){
//		Stage stage = stageService.findOne(1526);
//		Assert.notNull(stage);
//		
//		try{
//			stageService.delete(stage);
//			System.out.println("Stage deleted");
//		}catch(Throwable oops){
//			System.out.println(oops.getMessage());
//		}
		
//		Collection<Stage> stagesBefore = stageService.findAll();
//		stageService.delete(stage);
//		Collection<Stage> stagesAfter = stageService.findAll();
//		Assert.isTrue(stagesBefore.contains(stage) && !stagesAfter.contains(stage));
	}


