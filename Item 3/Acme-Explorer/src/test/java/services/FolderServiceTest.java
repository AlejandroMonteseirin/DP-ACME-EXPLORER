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
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class FolderServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private FolderService folderService;

	@Test
	public void testCreateFolder() {
		authenticate("manager1");
		Folder f = null;
		f = folderService.create();
		Assert.notNull(f);
		authenticate(null);
	}

	@Test
	public void testSaveFolder() {
		authenticate("manager1");
		Folder folder,saved;
		Collection<Folder> folders;
		folder = folderService.create();
		folder.setName("folder-1");
		saved = folderService.save(folder);
		folders = folderService.findAll();
		Assert.isTrue(folders.contains(saved));
		authenticate(null);
	}
	
	@Test
	public void testDeleteFolder(){
		authenticate("manager1");
		Folder folder,saved;
		Collection<Folder> folders;
		folder = folderService.create();
		folder.setName("folder-1");
		saved = folderService.save(folder);
		folderService.delete(saved);
		folders = folderService.findAll();
		Assert.isTrue(!folders.contains(saved));
		
		
	}

	//
	// @Test
	// public void createSaveDelete() {
	// authenticate("manager1");
	// Folder f, fSaved;
	// Collection<Message> messages = new ArrayList<>();
	// f = folderService.create();
	// Assert.notNull(f);
	//
	// // Probamos save
	// f.setName("name");
	// f.setMessages(messages);
	// fSaved = folderService.save(f);
	// Integer fSavedId = fSaved.getId();
	// Assert.isTrue(folderService.findOne(fSavedId).equals(fSaved));
	//
	// // Probamos delete
	// folderService.delete(fSaved);
	// Folder f2 = folderService.findOne(fSavedId);
	// Assert.isTrue(folderService.findOne(fSavedId)==null);
	// }

}
