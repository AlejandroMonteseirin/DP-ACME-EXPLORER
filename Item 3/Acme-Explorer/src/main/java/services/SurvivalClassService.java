package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.constraints.AssertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import repositories.SurvivalClassRepository;
import repositories.TripRepository;
import security.LoginService;
import security.UserAccount;
import domain.Explorer;
import domain.Location;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class SurvivalClassService {

	// Managed repository
	@Autowired
	private SurvivalClassRepository survivalClassRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private TripService tripService;

	// Constructor
	public SurvivalClassService() {
		super();
	}

	// Simple CRUD methods
	public SurvivalClass create() {
		SurvivalClass sc;

		Collection<Explorer> explorers;
		Location location = new Location();

		// location.setName("empty coordinates");
		// location.setCoordinates("0.0, 0.0");

		explorers = new ArrayList<>();

		sc = new SurvivalClass();
		sc.setExplorers(explorers);

		sc.setLocation(location);

		return sc;
	}

	public SurvivalClass save(SurvivalClass survivalClass) {
		// checkPrincipal(survivalClass);

		SurvivalClass sc;
		sc = survivalClassRepository.save(survivalClass);

		Manager m = (Manager) actorService.findByPrincipal();
		Collection<SurvivalClass> sC = m.getSurvivalClasses();
		if (!sC.contains(survivalClass)) {
			sC.add(sc);
			m.setSurvivalClasses(sC);
			managerService.save(m);
		}

		return sc;
	}

	public void delete(SurvivalClass survivalClass) {
		// checkPrincipal(survivalClass);
		Assert.notNull(survivalClass);
		Manager m = (Manager) actorService.findByPrincipal();
		Collection<SurvivalClass> sC = m.getSurvivalClasses();
		Assert.isTrue(sC.contains(survivalClass));
		sC.remove(survivalClass);

		survivalClassRepository.delete(survivalClass);
	}

	public Collection<SurvivalClass> findAll() {
		Collection<SurvivalClass> sc;

		sc = survivalClassRepository.findAll();
		Assert.notNull(sc);

		return sc;
	}

	public SurvivalClass findOne(int survivalClassId) {
		Assert.notNull(survivalClassId);
		
		SurvivalClass sc;
		
		sc = survivalClassRepository.findOne(survivalClassId);
		
		return sc;
	}
	
	public SurvivalClass findOneToEdit(int survivalClassId) {
		Assert.notNull(survivalClassId);
		
		SurvivalClass sc;
		
		sc = survivalClassRepository.findOne(survivalClassId);
		
		checkPrincipal(sc);
		
		return sc;
	}

	// Atend y unnatend, survival clases comentado debido a que no hay
	// bidirecionalidad, si la hubiera descomentar
	public void attend(int survivalClassId) {
		Explorer explorer;
		Collection<Explorer> explorers;
		SurvivalClass survivalClass;
		// Collection<SurvivalClass> survivalClasses;

		explorer = (Explorer) actorService.findByPrincipal();
		// survivalClasses = explorer.getSurvivalClasses();
		survivalClass = survivalClassRepository.findOne(survivalClassId);
		explorers = survivalClass.getExplorers();

		// Assert.isTrue(!survivalClasses.contains(survivalClass));7
		Assert.isTrue(!explorers.contains(explorer));

		Collection<Explorer> explorersNew;
		explorersNew = explorers;
		explorersNew.add(explorer);
		survivalClass.setExplorers(explorersNew);
		// Not necessary: customerService.save(customer);
		survivalClassRepository.save(survivalClass);
	}

	public void unAttend(int survivalClassId) {
		Explorer explorer;
		Collection<Explorer> explorers;
		SurvivalClass survivalClass;
		// Collection<SurvivalClass> survivalClasses;

		explorer = (Explorer) actorService.findByPrincipal();
		// survivalClasses = explorer.getSurvivalClasses();
		survivalClass = survivalClassRepository.findOne(survivalClassId);
		explorers = survivalClass.getExplorers();

		// Assert.isTrue(!survivalClasses.contains(survivalClass));7
		Assert.isTrue(explorers.contains(explorer));

		Collection<Explorer> explorersNew;
		explorersNew = explorers;
		explorersNew.remove(explorer);
		survivalClass.setExplorers(explorersNew);
		// Not necessary: customerService.save(customer);
		survivalClassRepository.save(survivalClass);
	}

	// a las que esta registradas y tiene una aplication de true

	public Collection<SurvivalClass> findRegistered() {

		Explorer explorer;
		Collection<SurvivalClass> survivalClassesApplication = new HashSet<>();
		Collection<SurvivalClass> survivalClassesRegistered = new HashSet<>();
		Collection<SurvivalClass> res = new HashSet<>();
		explorer = (Explorer) this.actorService.findByPrincipal();
		Collection<Trip> trips = tripService
				.getTripsExplorerApplication(explorer.getId());

		for (Trip t : trips) {
			survivalClassesApplication.addAll(survivalClassRepository
					.getSurvivalClassesTrip(t.getId()));
		}

		survivalClassesRegistered = survivalClassRepository
				.findByExplorerId(explorer.getId());

		res.addAll(survivalClassesApplication);
		res.retainAll(survivalClassesRegistered);

		return res;

	}

	public Collection<SurvivalClass> findNotRegistered() {
		Explorer explorer;
		Collection<SurvivalClass> survivalClassesApplication = new HashSet<>();
		Collection<SurvivalClass> survivalClassesRegistered = new HashSet<>();
		Collection<SurvivalClass> res = new HashSet<>();
		explorer = (Explorer) this.actorService.findByPrincipal();
		Collection<Trip> trips = tripService
				.getTripsExplorerApplication(explorer.getId());

		for (Trip t : trips) {
			survivalClassesApplication.addAll(survivalClassRepository
					.getSurvivalClassesTrip(t.getId()));
		}

		survivalClassesRegistered = survivalClassRepository
				.findByNotExplorerId(explorer.getId());

		res.addAll(survivalClassesApplication);
		res.retainAll(survivalClassesRegistered);

		return res;
	}
	
	public void checkPrincipal(SurvivalClass sc){
		Manager m;
		
		m = (Manager) actorService.findByPrincipal();
		
		Assert.isTrue(m.getSurvivalClasses().contains(sc));
	}

}
