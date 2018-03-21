package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.Message;
import domain.Notification;

@Service
@Transactional
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;

	// Constructor
	public NotificationService() {
		super();
	}

	public Notification create() {
		Notification result;
		result = new Notification();
		Administrator administrator = (Administrator) actorService
				.findByPrincipal();
		result.setSender(administrator);
		return result;
	}

	public void sendNotification(Notification notification) {
		
		Assert.notNull(notification);
		
		Administrator principal = (Administrator) actorService
				.findByPrincipal();
		
		for(Actor recipient: actorService.findAll()){
			if(!recipient.equals(principal)){
				Folder notificationbox = folderService.getNotificationBoxFolderFromActorId(recipient.getId());
				Notification copy = notification;
				Notification saved = this.notificationRepository.save(copy);
				notificationbox.getNotifications().add(saved);
			}
		}
		
		

	}

}
