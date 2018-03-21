package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Contact;
import domain.Explorer;

import repositories.ContactRepository;

@Service
@Transactional
public class ContactService {
	
	//Managed repository
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ActorService actorService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private ConfigurationService configurationService;
	
	
	//Constructor
	public ContactService(){
		super();
	}
	
	//Simple CRUD methods
	
	// SIMPLE CRUD METHODS -----------

		public Contact create(){
			
			Contact contact = new Contact();
			
			return contact;
			
		}
		
	
		
		public Contact save(Contact contact) {
			Assert.notNull(contact);
			

			Contact result;
			Assert.isTrue((contact.getEmail()!=null && contact.getEmail()!=("")) || (contact.getPhoneNumber()!=null && contact.getPhoneNumber()!=("")));
			result = contactRepository.save(contact);
			
			Explorer explorer =  explorerService.findByPrincipal();
			Collection<Contact> c=explorer.getContacts();
			if(!c.contains(contact)){
			Collection<Contact> cNew= c;
			
			cNew.add(result);
			explorer.setContacts(cNew);
			explorerService.save(explorer);
			}
			
			String tlf = configurationService.checkPhoneNumber(contact.getPhoneNumber());
			contact.setPhoneNumber(tlf);
			

			return result;
		}
		
		public Collection<Contact> findAll() {
			Collection<Contact> result;

			result = contactRepository.findAll();
			Assert.notNull(result);

			return result;
		}
		
		public Contact findOne(int contactId) {
			Contact result;

			result = contactRepository.findOne(contactId);

			return result;
		}
		
		public Contact findOneToEdit(int contactId) {
			Contact result;

			result = contactRepository.findOne(contactId);
			
			checkPrincipal(result);
			
			return result;
		}

		public void delete(Contact contact) {
			Assert.notNull(contact);
			Assert.isTrue(contact.getId() != 0);

			Explorer  s = (Explorer) actorService.findByPrincipal();
			Collection<Contact> c =s.getContacts();
			c.remove(contact);
			Collection<Contact> cnew =c;
			s.setContacts(cnew);
			explorerService.save(s);
			
			contactRepository.delete(contact);
		}
		
	//Other bussiness methods
		
		public void checkPrincipal(Contact c){
			Explorer e;
			
			e = (Explorer) actorService.findByPrincipal();
			
			Assert.isTrue(e.getContacts().contains(c));
		}

}
