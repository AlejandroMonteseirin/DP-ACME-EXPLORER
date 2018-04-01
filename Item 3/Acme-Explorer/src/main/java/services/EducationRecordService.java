package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.Ranger;

@Service
@Transactional
public class EducationRecordService {

	// MANAGED REPOSITORY ---------------
	@Autowired
	private EducationRecordRepository educationRecordRepository;

	// SUPPORTING SERVICES -------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private RangerService rangerService;

	// CONSTRUCTOR ---------------

	public EducationRecordService() {
		super();
	}

	// SIMPLE CRUD METHODS -----------

	public EducationRecord create() {

		EducationRecord er;
		er = new EducationRecord();

		return er;

	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationRecord findOne(final int educationRecordId) {
		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);

		return result;
	}
	
	public EducationRecord findOneToEdit(final int educationRecordId) {
		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);
		
		checkPrincipal(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		if(educationRecord.getEndDate() != null){
		Assert.isTrue(educationRecord.getStartDate().before(educationRecord.getEndDate()),"message.error.startDateEndDate");
		}
		Ranger r;
		Collection<EducationRecord> c;
		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);
		r = (Ranger) this.actorService.findByPrincipal();

	
			c = r.getCurriculum().getEducationRecords();
			if(!c.contains(result)){
				c.add(result);
				r.getCurriculum().setEducationRecords(c);
				rangerService.save(r);
				}
		// Comprobamos si es spam
		administratorService.checkIsSpam(educationRecord.getAttachmentURL());
		administratorService.checkIsSpam(educationRecord.getComment());
		administratorService.checkIsSpam(educationRecord.getDiplomaTitle());
		administratorService.checkIsSpam(educationRecord.getInstitutionName());

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		Ranger r;
		Collection<EducationRecord> c;

		r = (Ranger) this.actorService.findByPrincipal();

		c = r.getCurriculum().getEducationRecords();
		c.remove(educationRecord);
		r.getCurriculum().setEducationRecords(c);

		this.educationRecordRepository.delete(educationRecord);

	}

	// Other business methods
	
	public void checkPrincipal(EducationRecord er) {
		Ranger r;

		r = (Ranger) actorService.findByPrincipal();

		Assert.isTrue(r.getCurriculum().getEducationRecords().contains(er));
	}

}
