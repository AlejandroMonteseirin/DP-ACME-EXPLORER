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

import utilities.AbstractTest;
import domain.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ContactServiceTest extends AbstractTest{
	
	//Service under test
	@Autowired
	private ContactService contactService;
	
	@Test
	public void createSaveAnDelete(){
		Contact c;
		Contact cSaved;
		Collection<Contact> contactsBefore = new ArrayList<>();
		Collection<Contact> contactsAfter = new ArrayList<>();
		c = contactService.create();
		
		Assert.notNull(c);
		
		c.setEmail("email@email.es");
		c.setName("name");
		c.setPhoneNumber("123456789");
		cSaved = contactService.save(c);
		
		Assert.notNull(cSaved);
		
		contactsBefore = contactService.findAll();
		Assert.isTrue(contactsBefore.contains(cSaved));
		
		contactService.delete(cSaved);
		contactsAfter = contactService.findAll();
		
		Assert.isTrue(!contactsAfter.contains(cSaved));
	}
	

}
