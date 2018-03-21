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
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest{
	
	// Service under test ---------------------------------
	@Autowired
	private CreditCardService creditCardService;
	
	@Test
	public void createSaveAndDelete(){
		CreditCard cc;
		CreditCard ccSaved;
		Collection<CreditCard> ccBefore = new ArrayList<>();
		Collection<CreditCard> ccAfter = new ArrayList<>();
		
		cc = creditCardService.create();
		Assert.notNull(cc);
		
		cc.setBrandName("brandName");
		cc.setCVV(123);
		cc.setExpirationMonth(7);
		cc.setExpirationYear(2018);
		cc.setHolderName("holderName");
		cc.setNumber("4485545928304054");
		
		ccSaved = creditCardService.save(cc);
		Assert.notNull(ccSaved);
		
		ccBefore = creditCardService.findAll();
		Assert.isTrue(ccBefore.contains(ccSaved));
		
		creditCardService.delete(ccSaved);
		
		ccAfter = creditCardService.findAll();
		Assert.isTrue(!ccAfter.contains(ccSaved));
	}

	

}
