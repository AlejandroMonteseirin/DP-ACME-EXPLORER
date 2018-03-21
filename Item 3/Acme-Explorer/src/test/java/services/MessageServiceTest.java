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

import security.LoginService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Auditor;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService messageService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;

	@Test
	public void testCreateMessage() {
		Message message = null;
		message = messageService.create();
		Assert.notNull(message);

	}

	@Test
	public void testSaveMessage() {
		authenticate("manager1");
		Message message, saved;
		Collection<Message> messages;
		message = messageService.create();
		message.setSubject("subject1");
		message.setBody("body1");
		message.setPriority("HIGH");
		message.setSender(actorService.findByPrincipal());
		message.setRecipient(actorService.findOne(3290));
		saved = messageService.save(message);
		Assert.notNull(saved);
		messages = messageService.findAll();
		Assert.isTrue(messages.contains(saved));
		authenticate(null);
	}

	@Test
	public void testDeleteMessageFromTrashBox() {
		authenticate("manager1");
		Collection<Message> messages;
		Message message = messageService.findOne(3445);
		messageService.delete(message);
		messages = messageService.findAll();
		Assert.isTrue(!messages.contains(message));
		authenticate(null);
	}

	@Test
	public void testDeleteMessageFromOther() {
		authenticate("manager1");
		Folder trashbox=null;
		Actor manager1 = actorService.findByPrincipal();
		Message message = messageService.findOne(3361);
		messageService.delete(message);
		for(Folder f:manager1.getFolders()){
			if(f.getName().equals("trash box")){
				trashbox = f;
			}
		}
		Assert.isTrue(trashbox.getMessages().contains(message));
		authenticate(null);
	}

	// @Test
	// public void createAndSaveMessage() {
	//
	// authenticate("explorer1");
	//
	// Message message = messageService.create();
	//
	// //Probamos save
	// message.setBody("body 1");
	// message.setPriority("NEUTRAL");
	// message.setRecipient((Actor) actorService.findAll().toArray()[0]);
	// message.setSubject("subject 1");
	//
	// Message mSaved = messageService.save(message);
	// Assert.notNull(message);
	//
	// Folder outboxBefore = null;
	// for(Folder f : actorService.findByPrincipal().getFolders()){
	// if(f.getName().equals("out box")){
	// outboxBefore = f;
	// }
	// }
	// Assert.isTrue(outboxBefore.getMessages().contains(mSaved));
	//
	// //Probamos delete
	// // //Ya que el mensaje se encuentra en outbox, al borrarlo se moverá a
	// trashbox
	// messageService.delete(mSaved);
	//
	// Folder outboxAfter = null;
	// for(Folder f : actorService.findByPrincipal().getFolders()){
	// if(f.getName().equals("out box")){
	// outboxAfter = f;
	// }
	// }
	// Assert.isTrue(!outboxAfter.getMessages().contains(mSaved));
	//
	// Folder trashboxBefore = null;
	// for(Folder f : actorService.findByPrincipal().getFolders()){
	// if(f.getName().equals("trash box")){
	// trashboxBefore = f;
	// }
	// }
	// Assert.isTrue(trashboxBefore.getMessages().contains(mSaved));
	//
	// //Si volvemos a borrarlo, debe eliminarse de trashbox
	// messageService.delete(mSaved);
	// Folder trashboxAfter = null;
	// for(Folder f : actorService.findByPrincipal().getFolders()){
	// if(f.getName().equals("trash box")){
	// trashboxAfter = f;
	// }
	// }
	//
	// Assert.isTrue(!trashboxAfter.getMessages().contains(mSaved));
	//
	// }

	// @Test
	// public void DeleteMessage() {
	//
	// Message message = messageService.findOne(2558);
	// messageService.delete(message);
	// }
	//
	// @Test
	// public void SearchMessage() {
	// Collection<Message> messages = messageService.findAll();
	// Message message = messageService.findOne(2558);
	// System.out.println(message.getSender());
	// Assert.isTrue(messages.contains(message));
	//
	// }

}
