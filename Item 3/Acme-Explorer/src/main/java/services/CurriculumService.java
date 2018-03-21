package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculumService {

	// MANAGED REPOSITORY ---------------

	@Autowired
	private CurriculumRepository curriculumRepository;

	// SUPPORTING SERVICES -------------
	@Autowired
	private TripService tripService;
	@Autowired
	private RangerService rangerService;
	@Autowired
	private ActorService actorService;
	

	// CONSTRUCTOR ---------------

	public CurriculumService() {
		super();
	}

	// SIMPLE CRUD METHODS -----------

	public Curriculum create() {
		Curriculum c;
		String ticker;
		Collection<EndorserRecord> endorserRecords;
		Collection<ProfessionalRecord> professionalRecords;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Collection<EducationRecord> educationRecords;

		ticker = tripService.getTicker();
		endorserRecords = new ArrayList<>();
		professionalRecords = new ArrayList<>();
		miscellaneousRecords = new ArrayList<>();
		educationRecords = new ArrayList<>();

		c = new Curriculum();
		c.setTicker(ticker);
		c.setEndorserRecords(endorserRecords);
		c.setProfessionalRecords(professionalRecords);
		c.setMiscellaneousRecords(miscellaneousRecords);
		c.setEducationRecords(educationRecords);

		return c;
	}

	public Curriculum save(Curriculum curriculum) {
		Assert.notNull(curriculum);
		
		Ranger r;
		
		r = (Ranger) actorService.findByPrincipal();
		
		if(curriculum.getId()!=0){
		checkPrincipal(curriculum.getId());
		}
		
		
		Curriculum c = curriculumRepository.save(curriculum);
		
		r.setCurriculum(c);
		rangerService.save(r);
		

		return c;
	}

	public void delete(Curriculum curriculum) {
		Assert.notNull(curriculum);
		checkPrincipal(curriculum.getId());
		//prService.delete(curriculum.getPersonalRecord());
		
		Ranger r;
		r = (Ranger) actorService.findByPrincipal();
		r.setCurriculum(null);
		rangerService.save(r);
		curriculumRepository.delete(curriculum);
	}

	public Collection<Curriculum> findAll() {
		return curriculumRepository.findAll();
	}

	public Curriculum findOne(int curriculumId) {
		Assert.notNull(curriculumId);
		
		Curriculum c;
		
		c = curriculumRepository.findOne(curriculumId);
		
		return c;
	}
	
	public Curriculum findOneToEdit(int curriculumId) {
		Assert.notNull(curriculumId);
		
		Curriculum c;
		
		c = curriculumRepository.findOne(curriculumId);
		
		checkPrincipal(c.getId());
		
		return c;
	}

	// Other business methods
	public Curriculum getCurriculumByRangerId(int rangerId) {
		Assert.notNull(rangerId);

		return curriculumRepository.getCurriculumByRangerId(rangerId);
	}

	public void checkPrincipal(int curriculumId) {
		Ranger a;
		Ranger r;

		a = (Ranger) actorService.findByPrincipal();
		r = rangerService.getRangerByCurriculumId(curriculumId);
		Assert.isTrue(a.equals(r));
	}

}
