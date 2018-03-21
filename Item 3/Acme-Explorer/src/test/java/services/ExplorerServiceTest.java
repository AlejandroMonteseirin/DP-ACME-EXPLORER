package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccountService;
import utilities.AbstractTest;
import domain.Explorer;
import domain.Finder;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	//Service under test
		@Autowired
		private ExplorerService explorerService;
		@Autowired
		private UserAccountService userAccountService;
		
		@Test
		public void createSaveDelete(){
			Explorer admin, aSaved;
			Collection<Explorer> aBefore, aAfter;
			
			admin = explorerService.create();
			Assert.notNull(admin);
			
			//Comprobamos save
			admin.setName("name");
			admin.setSurname("surname");
			admin.setEmail("email@email.es");
			
			aSaved = explorerService.save(admin);
			Assert.notNull(aSaved);
			
			aBefore = explorerService.findAll();
			Assert.isTrue(aBefore.contains(aSaved));
			
			//Comprobamos delete
			explorerService.delete(aSaved);
			
			aAfter = explorerService.findAll();
			
			Assert.isTrue(!aAfter.contains(aSaved));
		}

}
