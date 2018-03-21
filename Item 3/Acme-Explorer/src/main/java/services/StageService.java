package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StageRepository;
import domain.Stage;
import domain.Trip;

@Service
@Transactional
public class StageService {

	// Managed repository
	@Autowired
	private StageRepository stageRepository;
	
	// Supporting services
	@Autowired
	private TripService tripService;
	@Autowired
	private AdministratorService administratorService;


	// Constructor
	public StageService() {
		super();
	}

	// Simple CRUD methods
	public Stage create(int tripId) {
		Stage res = new Stage();
		
		Trip trip = tripService.findOne(tripId);
		res.setTrip(trip);
		
		return res;
	}

	public Collection<Stage> findAll() {
		Collection<Stage> res = stageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Stage findOne(int stageId) {
		return stageRepository.findOne(stageId);
	}

	public Stage save(Stage stage) {
		Assert.notNull(stage);
		Stage saved = stageRepository.save(stage);
		
		Collection<Stage> stages = stage.getTrip().getStages();
		stages.add(saved);
		stage.getTrip().setStages(stages);
		
		tripService.save(stage.getTrip());
		
		//Comprobamos si es spam
		administratorService.checkIsSpam(stage.getTitle());
		administratorService.checkIsSpam(stage.getDescription());
		
		return saved;
	}

	public void delete(Stage stage) {
		Assert.notNull(stage);
		Assert.isTrue(stage.getId() != 0);

		stageRepository.delete(stage);
	}

	// other

}
