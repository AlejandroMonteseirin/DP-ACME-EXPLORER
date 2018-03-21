package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;
import domain.Ranger;

@Service
@Transactional
public class PersonalRecordService {
	
	//Managed repository
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	//Supporting services
	@Autowired
	private ActorService actorService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private AdministratorService administratorService;
	
	//Constructor
	public PersonalRecordService(){
		super();
	}
	
	//Simple CRUD methods
	public PersonalRecord create(){
		PersonalRecord personalRecord;
		personalRecord= new PersonalRecord();
		
		return personalRecord;
	}
	
	public PersonalRecord save(PersonalRecord personalRecord){
		Assert.notNull(personalRecord);
		
		PersonalRecord pr;
		
		pr = personalRecordRepository.save(personalRecord);
		
		// Comprobamos si es spam
		administratorService.checkIsSpam(personalRecord.getFullName());
		administratorService.checkIsSpam(personalRecord.getEmail());
		administratorService.checkIsSpam(personalRecord.getLinkedInProfileUrl());
		administratorService.checkIsSpam(personalRecord.getPhotoUrl());
		
		String tlf = configurationService.checkPhoneNumber(personalRecord.getPhoneNumber());
		personalRecord.setPhoneNumber(tlf);
		
		return pr;
		
	}
	
	public Collection<PersonalRecord> findAll(){
		return personalRecordRepository.findAll();
	}
	
	public PersonalRecord findOne(int personalRecordId){
		PersonalRecord result;

		result = personalRecordRepository.findOne(personalRecordId);
		

		return result;
	}
	
	public PersonalRecord findOneToEdit(int personalRecordId) {
		PersonalRecord result;

		result = personalRecordRepository.findOne(personalRecordId);
		
		checkPrincipal(result);

		return result;
	}
	
	public void delete(PersonalRecord pr){
		personalRecordRepository.delete(pr);
	}
	
	//Other business methods
	
	public void checkPrincipal(PersonalRecord mr){
		Ranger r;
		
		r = (Ranger) actorService.findByPrincipal();
		
		Assert.isTrue(r.getCurriculum().getPersonalRecord().equals(mr));
	}
	
	

}
