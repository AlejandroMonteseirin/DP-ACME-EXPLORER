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

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class RangerServiceTest extends AbstractTest {

	@Autowired
	private RangerService rangerService;
	@Autowired
	private UserAccountService userAccountService;
	
	@Test
	public void createSaveDelete(){
		Ranger admin, aSaved;
		Collection<Ranger> aBefore, aAfter;
		
		admin = rangerService.create();
		Assert.notNull(admin);
		
		//Comprobamos save
		UserAccount ua = userAccountService.create();
		Collection<Authority> auth = new ArrayList<Authority>();
		Authority au = new Authority();
		au.setAuthority(Authority.MANAGER);
		auth.add(au);
		ua.setAuthorities(auth);
		ua.setEnabled(true);
		ua.setUsername("prueba");
		ua.setPassword("prueba");
		admin.setUserAccount(ua);
		
		admin.setName("name");
		admin.setSurname("surname");
		admin.setEmail("email@email.es");
		
		aSaved = rangerService.save(admin);
		Assert.notNull(aSaved);
		
		aBefore = rangerService.findAll();
		Assert.isTrue(aBefore.contains(aSaved));
		
		//Comprobamos delete
		rangerService.delete(aSaved);
		
		aAfter = rangerService.findAll();
		
		Assert.isTrue(!aAfter.contains(aSaved));
	}

	// @Test
	// public void testCreateRanger() {
	// Ranger ranger = null;
	// ranger = rangerService.create();
	// Assert.notNull(ranger);
	// }
	//
	// @Test
	// public void testSaveRanger() {
	//
	// Ranger ranger, saved;
	// Collection<Ranger> rangers;
	// ranger = rangerService.create();
	// ranger.setName("Name1");
	// ranger.setSurname("Surname1");
	// saved = rangerService.save(ranger);
	// rangers = rangerService.findAll();
	// Assert.isTrue(rangers.contains(saved));
	//
	// }
	//
	// @Test
	// public void testDeleteRanger() {
	//
	// Ranger ranger, saved;
	// Collection<Ranger> rangers;
	// ranger = rangerService.create();
	// ranger.setName("Name1");
	// ranger.setSurname("Surname1");
	// saved = rangerService.save(ranger);
	// rangerService.delete(saved);
	// rangers = rangerService.findAll();
	// Assert.isTrue(!rangers.contains(saved));
	//
	// }
	//
	// @Test
	// public void testFindOneRanger() {
	// Ranger ranger, saved;
	// ranger = rangerService.create();
	// ranger.setFolders(new ArrayList<Folder>());
	// ranger.setName("Name1");
	// ranger.setSurname("Surname1");
	// saved = rangerService.save(ranger);
	// int rangerId = saved.getId();
	// Ranger r = rangerService.findOne(rangerId);
	// Assert.isTrue(r.equals(saved));
	//
	// }
//	@Test
//	public void testFindByTripId() {
//		Ranger ranger = rangerService.findByTripId(1086);
//		Assert.isTrue(ranger.getId() == 1004);
//	}

//	@Test
//	public void testGetRegisteredCurriculaRatio() {
//		Double ratio = rangerService.getRegisteredCurriculaRatio();
//		Assert.isTrue(ratio == 0.5);
//	}
//
//	@Test
//	public void testEndorsedCurriculaRatio() {
//		Double ratio = rangerService.getEndorsedCurriculaRatio();
//		Assert.isTrue(ratio == 1.0);
//	}
//
//	@Test
//	public void testGetSuspiciousRangersRatio() {
//
//		Double ratio = rangerService.getSuspiciousRangersRatio();
//		Assert.isTrue(ratio == 0.5);
//
//	}
//
//	@Test
//	public void testGetSuspiciousRangers() {
//
//		Collection<Ranger> suspiciousRangers = rangerService
//				.getSuspiciousRangers();
//		for (Ranger r : suspiciousRangers) {
//			Assert.isTrue(r.getSuspicious());
//		}
//	}
//	@Test
//	public void testGetRangerByCurriculumId(){
//		Ranger ranger = rangerService.getRangerByCurriculumId(1103);
//		Assert.isTrue(ranger.getId()==1004);
//	}
}