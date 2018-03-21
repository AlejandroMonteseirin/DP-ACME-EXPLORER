package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.Ranger;

@Service
@Transactional
public class EndorserRecordService {

	// MANAGED REPOSITORY ---------------
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;

	// SUPPORTING SERVICES -------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private RangerService rangerService;
	@Autowired
	private ConfigurationService configurationService;

	// CONSTRUCTOR ---------------

	public EndorserRecordService() {
		super();
	}

	// SIMPLE CRUD METHODS -----------

	public EndorserRecord create() {

		EndorserRecord er;
		er = new EndorserRecord();

		return er;

	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;

		result = endorserRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord findOne(int endorserRecordId) {
		EndorserRecord result;

		result = endorserRecordRepository.findOne(endorserRecordId);

		return result;
	}

	public EndorserRecord findOneToEdit(int endorserRecordId) {
		EndorserRecord result;

		result = endorserRecordRepository.findOne(endorserRecordId);

		checkPrincipal(result);

		return result;
	}

	public EndorserRecord save(EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		Ranger r;
		Collection<EndorserRecord> c;
		EndorserRecord result;

		result = this.endorserRecordRepository.save(endorserRecord);
		r = (Ranger) this.actorService.findByPrincipal();

		if (endorserRecord.getId() == 0) {
			c = r.getCurriculum().getEndorserRecords();
			c.add(result);
			r.getCurriculum().setEndorserRecords(c);
			rangerService.save(r);
		}
		
		// Comprobamos si es spam
		administratorService.checkIsSpam(endorserRecord.getComment());
		administratorService.checkIsSpam(endorserRecord.getEmail());
		administratorService.checkIsSpam(endorserRecord.getFullName());
		administratorService
				.checkIsSpam(endorserRecord.getLinkedInProfileUrl());
		
		String tlf = configurationService.checkPhoneNumber(endorserRecord.getPhoneNumber());
		endorserRecord.setPhoneNumber(tlf);

		return result;
	}

	public void delete(EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);

		Ranger r;
		Collection<EndorserRecord> c;

		r = (Ranger) this.actorService.findByPrincipal();

		c = r.getCurriculum().getEndorserRecords();
		c.remove(endorserRecord);
		r.getCurriculum().setEndorserRecords(c);

		this.endorserRecordRepository.delete(endorserRecord);
	}

	// Other business methods

	public void checkPrincipal(EndorserRecord er) {
		Ranger r;

		r = (Ranger) actorService.findByPrincipal();

		Assert.isTrue(r.getCurriculum().getEndorserRecords().contains(er));
	}

}
