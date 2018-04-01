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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest{
	
	//Service under test
	@Autowired
	private AdministratorService administratorService;
	
	@Test
	public void createSaveDelete(){
		Administrator admin, aSaved;
		Collection<Administrator> aBefore, aAfter;
		
		admin = administratorService.create();
		Assert.notNull(admin);
		
		//Comprobamos save
		admin.setName("name");
		admin.setSurname("surname");
		admin.setEmail("email@email.es");
		
		aSaved = administratorService.save(admin);
		Assert.notNull(aSaved);
		
		aBefore = administratorService.findAll();
		Assert.isTrue(aBefore.contains(aSaved));
		
		//Comprobamos delete
		administratorService.delete(aSaved);
		
		aAfter = administratorService.findAll();
		
		Assert.isTrue(!aAfter.contains(aSaved));
	}

}
