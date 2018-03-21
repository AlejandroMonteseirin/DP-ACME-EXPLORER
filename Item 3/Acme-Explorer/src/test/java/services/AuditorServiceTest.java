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
import domain.Auditor;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class AuditorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private AuditorService auditorService;

	// @Autowired
	// private AuditService auditService;
	// @Autowired
	// private FolderService folderService;
	// @Autowired
	// private MessageService messageService;

	// Tests ----------------------------------------------
	@Test
	public void testCreateSaveDelete() {
		Auditor a, aSaved;
		Collection<Auditor> aBefore, aAfter;
		
		a = auditorService.create();
		Assert.notNull(a);
		
		//Probamos save
		a.setName("name");
		a.setSurname("surname");
		a.setEmail("email@email.es");
		
		aSaved = auditorService.save(a);
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
