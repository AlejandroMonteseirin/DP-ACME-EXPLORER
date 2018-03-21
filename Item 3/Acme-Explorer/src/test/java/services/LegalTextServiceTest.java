package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.LegalText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class LegalTextServiceTest extends AbstractTest{

	
	// Service under test ---------------------------------
	@Autowired
		private LegalTextService legalTextService;
	
	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveAndDelete() {

//		LegalText legalText;
//
//		legalText = legalTextService.create();
//		Assert.notNull(legalText, "fallo en el create,es null");
//
//		legalText
//				.setBody("La organización no se hace responsable de la muerte por ataque de oso");
//		legalText.setTitle("Articulo 23, Ataques de Osos Homicidas");
//		List<String> applicableLaws = new ArrayList<>();
//		applicableLaws.add("ley 15");
//		applicableLaws.add("Articulo 33, Osos pardos");
//		legalText.setSavedMode("DRAFT MODE");
//		LegalText legalTextSaved = legalTextService.save(legalText);
//		Assert.notNull(legalTextSaved, "fallo en el save,es null");
//		Collection<LegalText> legalTextBefore = legalTextService.findAll();
//		Assert.isTrue(legalTextBefore.contains(legalTextSaved),
//				"fallo en el Save,no se guardo");
//		legalTextService.delete(legalTextSaved);
//		Collection<LegalText> legalTextAfter = legalTextService.findAll();
//
//		Assert.isTrue(!legalTextAfter.contains(legalTextSaved),
//				"fallo en el delete,no se borró");

	}
		
}
