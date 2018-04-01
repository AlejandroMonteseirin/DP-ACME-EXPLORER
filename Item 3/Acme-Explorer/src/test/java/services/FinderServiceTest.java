package services;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Finder;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class FinderServiceTest extends AbstractTest{
	
	//Service under test
	@Autowired
	private FinderService finderService;
	
	@Test
	public void testTodo() {
	super.authenticate("explorer1");
	Finder er;
	Finder erSav;

	er = this.finderService.create();
	Assert.notNull(er, "El objeto creado es nulo");
	
	er.setKeyWord("aprobado");
	erSav =finderService.save(er);
	Assert.notNull(erSav, "El objeto guardado es nulo");
	super.authenticate(null);
	}

}
