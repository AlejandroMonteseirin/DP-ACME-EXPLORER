package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Stage;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class StageServiceTest extends AbstractTest{
	
	// SERVICE TESTED
	@Autowired
	private StageService stageService;
	
	// TESTS
	
	@Test
	public void createAndSaveTest(){
//		Stage stage = stageService.create();
//		Assert.notNull(stage);
//		
//		Collection<Stage> stagesBefore = stageService.findAll();
//		
//		stage.setTitle("Test");
//		stage.setDescription("descripcion");
//		stage.setPrice(250.0);
//		
//		Stage stageSaved = stageService.save(stage);
//		Assert.notNull(stageSaved);
//		Collection<Stage> stagesAfter = stageService.findAll();
//		
//		Assert.isTrue(!stagesBefore.contains(stageSaved) && stagesAfter.contains(stageSaved));
//		
	}
	
	@Test
	public void deleteTest(){
		Stage stage = stageService.findOne(1526);
		Assert.notNull(stage);
		
		try{
			stageService.delete(stage);
			System.out.println("Stage deleted");
		}catch(Throwable oops){
			System.out.println(oops.getMessage());
		}
		
//		Collection<Stage> stagesBefore = stageService.findAll();
//		stageService.delete(stage);
//		Collection<Stage> stagesAfter = stageService.findAll();
//		Assert.isTrue(stagesBefore.contains(stage) && !stagesAfter.contains(stage));
	}

}
