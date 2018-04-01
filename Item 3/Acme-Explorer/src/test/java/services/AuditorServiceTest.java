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
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class AuditorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private UserAccountService userAccountService;

	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveDelete() {
		Auditor admin, aSaved;
		Collection<Auditor> aBefore, aAfter;
		
		admin = auditorService.create();
		Assert.notNull(admin);
		
		//Comprobamos save
		UserAccount ua = userAccountService.create();
		Collection<Authority> auth = new ArrayList<Authority>();
		Authority au = new Authority();
		au.setAuthority(Authority.AUDITOR);
		auth.add(au);
		ua.setAuthorities(auth);
		ua.setEnabled(true);
		ua.setUsername("prueba");
		ua.setPassword("prueba");
		admin.setUserAccount(ua);
		admin.setName("name");
		admin.setSurname("surname");
		admin.setEmail("email@email.es");
		admin.setPhoneNumber("132");
		
		aSaved = auditorService.save(admin);
		Assert.notNull(aSaved);
		
		aBefore = auditorService.findAll();
		Assert.isTrue(aBefore.contains(aSaved));
		
		//Comprobamos delete
		auditorService.delete(aSaved);
		
		aAfter = auditorService.findAll();
		
		Assert.isTrue(!aAfter.contains(aSaved));
	}

//	// // delete
//	@Test
//	public void findAuditor() {
//		Collection<Auditor> auditors = auditorService.findAll();
//		List<Auditor> aud = new ArrayList<Auditor>(auditors);
//
//		int auditId = aud.get(0).getId();
//		Auditor auditor = auditorService.findOne(auditId);
//
//		Assert.isTrue(auditors.contains(auditor));
//		System.out.println("direccion y nombre: " + auditor.getAddress()
//				+ auditor.getName());
//
//	}
	//
	// authenticate("auditor1");
	// Collection<Auditor> coll = auditorService.findAll();
	// List<Auditor> au = new ArrayList<>(coll);
	// Auditor auditor = auditorService.findOne(au.get(0).getId());
	//
	// for (Audit audit : auditor.getAudits()) {
	// auditService.delete(audit);
	// }
	//
	// auditorService.delete(auditor);
	// Collection<Auditor> collafter = auditorService.findAll();
	// Assert.isTrue(!collafter.contains(auditor));
	//
	// }

}
