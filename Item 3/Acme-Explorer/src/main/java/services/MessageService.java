package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Auditor;
import domain.Explorer;
import domain.Folder;
import domain.Manager;
import domain.Message;
import domain.Ranger;
import domain.Sponsor;

@Service
@Transactional
public class MessageService {

	// Managed repository
	@Autowired
	private MessageRepository messageRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;

	@Autowired
	private AdministratorService administratorService;

	// Constructor
	public MessageService() {
		super();
	}

	// Simple CRUD methods
	public Message create() {
		Message res;
		Date moment;
		res = new Message();
		Actor actor = actorService.findByPrincipal();
		moment = new Date(System.currentTimeMillis() - 1);
		res.setSender(actor);
		res.setMoment(moment);
		return res;
	}

	
	public Message save(Message message) {
		// Compruebo que no sea nulo el mensaje que me pasan
		Assert.notNull(message);
		Assert.notNull(message.getRecipient());
		// Inicializo el momento en el que se envía
		Date moment;
		// Inicializo el Folder del destinatario
		Folder recipientFolder;
		// Inicializo el mensaje guardado
		Message saved = null;
		// Si el mensaje que me pasan ya había estado guardado en la base de
		// datos (se quiere cambiar de Folder)
		// Si el mensaje se está guardando en la base de datos por
		// primera vez:
		// instancio el momento en que se envía como el momento actual
		moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		// guardo el mensaje en la base de datos
		saved = messageRepository.save(message);

		// Hago una copia del mensaje original, guardo la copia en la base
		// de datos y
		// lo añado a la colección de mensajes del sender
		Message copiedMessage = message;
		moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		Message copiedAndSavedMessage = messageRepository.save(copiedMessage);

		// Comprubeo si el mensaje contiene texto marcado como spam
		// si contiene spam
		if (administratorService.checkIsSpam(saved.getBody())
				|| administratorService.checkIsSpam(saved.getSubject())) {
			// instancio el Folder del destinatario como el spambox
			recipientFolder = folderService.getSpamBoxFolderFromActorId(saved
					.getRecipient().getId());
		} else {// si no contiene spam
			// instancio el Folder del destinatario como inbox
			recipientFolder = folderService.getInBoxFolderFromActorId(saved
					.getRecipient().getId());
		}
		// cojo los mensajes del Folder del destinatario
		Collection<Message> recipientFolderMessages = recipientFolder
				.getMessages();
		// Añado el mensaje guardado
		recipientFolderMessages.add(saved);
		// Actualizo el conjunto de mensajes
		recipientFolder.setMessages(recipientFolderMessages);
		// Cojo el sender
		Actor sender = actorService.findByPrincipal();
		// Cojo el outbox del sender
		Folder senderOutbox = folderService.getOutBoxFolderFromActorId(sender
				.getId());
		// Cojo los mensajes del outbox del sender
		Collection<Message> senderOutboxMessages = senderOutbox.getMessages();

		// Añado el mensaje guardado a los mensajes del outbox del sender
		senderOutboxMessages.add(copiedAndSavedMessage);
		// Actualizo los mensajes del outbox del sender
		senderOutbox.setMessages(senderOutboxMessages);
		folderService.save(senderOutbox);

		return saved;
	}

	public void saveToMove(Message message, Folder folder) {

		Assert.notNull(message);
		Assert.notNull(folder);
		
		
		Folder currentFolder = folderService.getFolderFromMessageId(message
				.getId());
		Collection<Message> currentFolderMessages = currentFolder.getMessages();
		currentFolderMessages.remove(message);
		currentFolder.setMessages(currentFolderMessages);
		folderService.simpleSave(currentFolder);
		
		//this.messageRepository.delete(message.getId());
		
		//Message savedCopy = this.messageRepository.save(copy);
		
		// Message saved = this.messageRepository.save(message);
		Collection<Message> folderMessages = folder.getMessages();
		folderMessages.add(message);
		folder.setMessages(folderMessages);
		folderService.simpleSave(folder);
		
		//this.messageRepository.save(message);

	}

	// public Message save(Message message) {
	// Assert.notNull(message);
	// Date moment;
	// moment = new Date(System.currentTimeMillis() - 1);
	// message.setMoment(moment);
	// Folder recipientFolder = new Folder();
	// //guardo el mensaje
	// messageRepository.save(message);
	// //si el mensaje no se ha guardado todavía
	// if(message.getId()==0){
	// //si es spam
	// if (administratorService.checkIsSpam(message.getBody())
	// || administratorService.checkIsSpam(message.getSubject())) {
	// //recipientFolder será el spambox del recipient del message
	// recipientFolder = folderService.getSpamBoxFolderFromActorId(message
	// .getRecipient().getId());
	// //si no es spam
	// } else {
	// //recipientFolder será el inbox del recipient del message
	// recipientFolder = folderService.getInBoxFolderFromActorId(message
	// .getRecipient().getId());
	// }
	// //saco los mensajes del recipientFolder
	// Collection<Message> messages = recipientFolder.getMessages();
	// //añado el mensaje al recipientFolder
	// messages.add(message);
	// recipientFolder.setMessages(messages);
	//
	// moment = new Date(System.currentTimeMillis() - 1);
	// message.setMoment(moment);
	// }
	//
	//
	// Actor a = actorService.findByPrincipal();
	//
	//
	// Folder outbox = null;
	// for(Folder f : a.getFolders()){
	// if(f.getName().equals("out box")){
	// outbox = f;
	// }
	// }
	// Message mSaved = messageRepository.save(message);
	// outbox.getMessages().add(mSaved);
	// folderService.save(outbox);
	// message.setSender(a);
	//
	// actorService.save(a);
	//
	// return mSaved;
	// }

	public void delete(Message message) {
		// Compruebo que el mensaje que me pasan no sea nulo
		Assert.notNull(message);
		// Saco el actor que está logueado
		Actor actor = actorService.findByPrincipal();
		// Compruebo que el mensaje que me pasan sea del actor que está logueado
		String type = actorService.getType(actor.getUserAccount());

		if (type.equals("EXPLORER")) {
			actor = (Explorer) actor;
		} else if (type.equals("AUDITOR")) {
			actor = (Auditor) actor;
		} else if (type.equals("RANGER")) {
			actor = (Ranger) actor;
		} else if (type.equals("MANAGER")) {
			actor = (Manager) actor;
		} else if (type.equals("SPONSOR")) {
			actor = (Sponsor) actor;
		}

		checkPrincipal(message, actor);
		// cojo el trashbox del actor logueado
		Folder trashbox = folderService.getTrashBoxFolderFromActorId(actor
				.getId());
		// Compruebo que el trashbox del actor logueado no sea nulo
		Assert.notNull(trashbox);
		// si el mensaje que me pasan está en el trashbox del actor logueado:
		if (trashbox.getMessages().contains(message)) {
			// saco la collection de mensajes del trashbox del actor logueado
			Collection<Message> trashboxMessages = trashbox.getMessages();
			// borro el mensaje que me pasan de la collection de mensajes del
			// trashbox
			trashboxMessages.remove(message);
			// actualizo la collection de mensajes del trashbox
			trashbox.setMessages(trashboxMessages);
			// borro el mensaje del sistema
			messageRepository.delete(message);

		} else {// si el mensaje que se quiere borrar no está en el trashbox:

			// Borro el mensaje del folder en el que estaba
			Folder messageFolder = folderService.getFolderFromMessageId(message
					.getId());
			Assert.notNull(messageFolder);
			Collection<Message> messages = messageFolder.getMessages();
			messages.remove(message);
			messageFolder.setMessages(messages);

			// Meto en el trashbox el mensaje
			Collection<Message> trashboxMessages = trashbox.getMessages();
			trashboxMessages.add(message);
			trashbox.setMessages(trashboxMessages);

		}
	}

	public void checkPrincipal(Message message, Actor actor) {
		Collection<Message> messages = messageRepository
				.messagesFromActorId(actor.getId());
		Assert.isTrue(messages.contains(message));
	}

	// public void delete(Message message) {
	// //Compruebo que no sea nulo
	// Assert.notNull(message);
	// //Saco el actor logueado
	// Actor a;
	// a = actorService.findByPrincipal();
	// //Si alguno de los Folders del actor logueado contiene el mensaje que
	// quiere borrar
	// Folder f = null;
	// for (Folder fo : a.getFolders()) {
	// if (fo.getMessages().contains(message)) {
	// //meto ese folder en f
	// f = fo;
	// }
	// }
	// //Cojo el trashbox del logueado
	// Folder trashbox = null;
	// for (Folder fo : a.getFolders()) {
	// if (fo.getName().equals("trash box")) {
	// trashbox = fo;
	// }
	// }
	// //si folder en el que estaba el mensaje era el trashbox
	// if (f.getName().equals("trash box")) {
	// //borro el mensaje
	// messageRepository.delete(message);
	// Collection<Message> m = f.getMessages();
	// m.remove(message);
	// f.setMessages(m);
	// folderService.save(f);
	// //si el folder en el que estaba el mensaje no era el trashbox
	// } else {
	// //borro el mensaje del folder en el que estuviera
	// Collection<Message> m1 = f.getMessages();
	// m1.remove(message);
	// f.setMessages(m1);
	// folderService.save(f);
	// //añado el mensaje al trashbox
	// Collection<Message> m = trashbox.getMessages();
	// m.add(message);
	// trashbox.setMessages(m);
	// folderService.save(trashbox);
	// }
	// }

	public void delete(Iterable<Message> messages) {
		Assert.notNull(messages);
		messageRepository.delete(messages);
	}

	public Message findOne(int messageId) {
		return messageRepository.findOne(messageId);

	}

	public Collection<Message> findAll() {
		return messageRepository.findAll();

	}

	// Other business methods

	public void sendApplicationNotification(Explorer e, Manager m) {
		Message managerMessage;
		Message explorerMessage;

		managerMessage = new Message();
		explorerMessage = new Message();

		// Enviamos el mensaje al manager

		managerMessage.setSubject("Status change");
		managerMessage
				.setBody("An application to one of your trips  has changed its status");
		managerMessage.setRecipient(m);
		managerMessage.setSender((Actor) administratorService.findAll()
				.toArray()[0]);

		Date moment = new Date(System.currentTimeMillis() - 1);
		managerMessage.setMoment(moment);
		Message saved = this.messageRepository.save(managerMessage);
		// Message result = this.save(managerMessage);

		Folder f = folderService.getNotificationBoxFolderFromActorId(m.getId());
		f.getMessages().add(saved);
		folderService.simpleSave(f);

		// Enviamos el mensaje al explorer

		explorerMessage.setSubject("Status change");
		explorerMessage
				.setBody("An application to one of your trips  has changed its status");
		explorerMessage.setRecipient(e);
		explorerMessage.setSender((Actor) administratorService.findAll()
				.toArray()[0]);

		Date moment2 = new Date(System.currentTimeMillis() - 1);
		explorerMessage.setMoment(moment2);
		Message saved2 = this.messageRepository.save(explorerMessage);
		// Message result2 = this.save(explorerMessage);

		Folder f2 = folderService
				.getNotificationBoxFolderFromActorId(e.getId());
		f2.getMessages().add(saved2);
		folderService.simpleSave(f2);
	}

	// public Message sendMessage() {
	// Message result;
	// Actor actor;
	// Folder outbox = null;
	// Date sentDate;
	//
	// actor = actorService.findByPrincipal();
	// outbox = folderService.getOutBoxFolderFromActorId(actor.getId());
	// sentDate = new Date();
	// Assert.notNull(outbox);
	//
	// result = create();
	// result.setSender(actor);
	// result.setMoment(sentDate);
	// outbox.getMessages().add(result);
	// messageRepository.save(result);
	// folderService.save(outbox);
	// return result;
	// }
}
