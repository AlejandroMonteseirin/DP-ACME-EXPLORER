package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class ProfessionalRecordService {

	// MANAGED REPOSITORY ---------------

	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;

	// SUPPORTING SERVICES -------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private RangerService rangerService;
	@Autowired
	private AdministratorService administratorService;

	// CONSTRUCTOR ---------------

	public ProfessionalRecordService() {
		super();
	}

	// SIMPLE CRUD METHODS -----------
	public ProfessionalRecord create() {

		ProfessionalRecord pr;

		pr = new ProfessionalRecord();

		return pr;

	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;

		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord findOne(final int EducationRecordId) {
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(EducationRecordId);

		return result;
	}

	public ProfessionalRecord findOneToEdit(int EducationRecordId) {
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(EducationRecordId);

		checkPrincipal(result);

		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord pr) {
		Assert.notNull(pr);
		if(pr.getEndDate() != null){
		Assert.isTrue(pr.getStartDate().before(pr.getEndDate()),"message.error.startDateEndDate");
		}
		Ranger r;
		Collection<ProfessionalRecord> c;
		ProfessionalRecord result;

		result = this.professionalRecordRepository.save(pr);
		r = (Ranger) this.actorService.findByPrincipal();

		if (pr.getId() == 0) {
			c = r.getCurriculum().getProfessionalRecords();
			c.add(pr);
			r.getCurriculum().setProfessionalRecords(c);
			rangerService.save(r);
		}

		// Comprobamos si es spam
		administratorService.checkIsSpam(pr.getAttachmentURL());
		administratorService.checkIsSpam(pr.getComment());
		administratorService.checkIsSpam(pr.getCompanyName());
		administratorService.checkIsSpam(pr.getPlayedRole());

		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		Ranger r;
		Collection<ProfessionalRecord> c;

		r = (Ranger) this.actorService.findByPrincipal();

		c = r.getCurriculum().getProfessionalRecords();
		c.remove(professionalRecord);
		r.getCurriculum().setProfessionalRecords(c);

		this.professionalRecordRepository.delete(professionalRecord);
	}

	// Other business methods

	public void checkPrincipal(ProfessionalRecord mr) {
		Ranger r;

		r = (Ranger) actorService.findByPrincipal();

		Assert.isTrue(r.getCurriculum().getProfessionalRecords().contains(mr));
	}

}
