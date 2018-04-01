package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Configuration;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest{
	
	// SERVICE UNDER TEST ------------------------
	@Autowired
	private ConfigurationService configService;
	
	// TESTS -------------------------------------
	
	@Test
	public void createAndSaveTest(){
		Configuration config = configService.create();
		Assert.notNull(config);
		
		Collection<Configuration> configsBefore = configService.findAll();
		
		Collection<String> spamWords = new ArrayList<String>();
		spamWords.add("palabra");
		
		config.setBannerURL("http://www.banner.es");
		config.setCountryCode("34");
		config.setFinderCached(2);
		config.setFinderReturn(20);
		config.setSpamWords(spamWords);
		config.setVATTax(24);
		config.setWelcomeMessageEN("Welcome");
		config.setWelcomeMessageES("Welcome");
		
		Configuration configSaved = configService.save(config);
		
		Collection<Configuration> configsAfter = configService.findAll();
		
		Assert.isTrue(!configsBefore.contains(configSaved));
		Assert.isTrue(configsAfter.contains(configSaved));
		
	}

}
