package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Service
@Transactional
public class MiscellaneousRecordService {
	// Managed repository
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private RangerService rangerService;

	// Constructor
	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods

	public MiscellaneousRecord create() {

		MiscellaneousRecord er;
		er = new MiscellaneousRecord();

		return er;

	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;

		result = miscellaneousRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord findOne(int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}

	public MiscellaneousRecord findOneToEdit(int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		checkPrincipal(result);

		return result;
	}

	public MiscellaneousRecord save(MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		Ranger r;
		Collection<MiscellaneousRecord> c;
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		r = (Ranger) this.actorService.findByPrincipal();

		if (miscellaneousRecord.getId() == 0) {
			c = r.getCurriculum().getMiscellaneousRecords();
			c.add(result);
			r.getCurriculum().setMiscellaneousRecords(c);
			rangerService.save(r);
		}

		// Comprobamos si es spam
		administratorService
				.checkIsSpam(miscellaneousRecord.getAttachmentURL());
		administratorService.checkIsSpam(miscellaneousRecord.getComment());
		administratorService.checkIsSpam(miscellaneousRecord.getTitle());

		return result;
	}

	public void delete(MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		Ranger r;
		Collection<MiscellaneousRecord> c;

		r = (Ranger) this.actorService.findByPrincipal();

		c = r.getCurriculum().getMiscellaneousRecords();
		c.remove(miscellaneousRecord);
		r.getCurriculum().setMiscellaneousRecords(c);

		miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	// Other business methods

	public void checkPrincipal(MiscellaneousRecord mr) {
		Ranger r;

		r = (Ranger) actorService.findByPrincipal();

		Assert.isTrue(r.getCurriculum().getMiscellaneousRecords().contains(mr));
	}
}
