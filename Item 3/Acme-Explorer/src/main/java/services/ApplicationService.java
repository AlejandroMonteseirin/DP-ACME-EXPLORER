package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Trip;

@Service
@Transactional
public class ApplicationService {

	// Managed repository
	@Autowired
	private ApplicationRepository applicationRepository;

	// Supported services
	@Autowired
	private ActorService actorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private CreditCardService creditCardService;
	@Autowired
	private TripService tripService;
	@Autowired
	private MessageService messageService;

	// Constructor
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create() {

		Application result;
		Date moment;
		Explorer e;

		result = new Application();
		moment = new Date(System.currentTimeMillis() - 1);
		e = (Explorer) this.actorService.findByPrincipal();

		result.setCreationMoment(moment);
		result.setStatus("PENDING");
		result.setExplorer(e);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application result;
		Collection<Application> applications;
		Explorer e;
		
		e = (Explorer) this.actorService.findByPrincipal();

		if (application.getCreditCard() != null) {
			final CreditCard c = this.creditCardService.save(application
					.getCreditCard());
			application.setCreditCard(c);
			application.setStatus("ACCEPTED");
			
			Manager m = managerService.getManagerFromApplicationId(application.getId());
			
			//messageService.sendApplicationNotification(e,m);
		}

		result = this.applicationRepository.save(application);

		if (application.getId() == 0) {
			applications = e.getApplications();
			applications.add(result);
			e.setApplications(applications);
			this.explorerService.save(e);
		}

		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application);

		this.applicationRepository.delete(application);

	}

	public Collection<Application> findAll() {

		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;
	}

	public Application findOne(final int applicationId) {

		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods
	public void checkPrincipal(final Application a) {
		Assert.notNull(a);

		Manager m = new Manager();
		Explorer e = new Explorer();

		if (this.actorService.getType(LoginService.getPrincipal()).equals(
				"MANAGER"))
			m = (Manager) this.actorService.findByPrincipal();
		else if (this.actorService.getType(LoginService.getPrincipal()).equals(
				"EXPLORER"))
			e = (Explorer) this.actorService.findByPrincipal();

		if (a.getStatus().equals("PENDING"))
			Assert.isTrue(m.equals(this.managerService
					.getManagerFromApplicationId(a.getId())));
		else if (a.getStatus().equals("ACCEPTED")) {
			e = this.explorerService.findByApplicationId(a.getId());
			Assert.isTrue(e.equals(e));
		}
	}

	public Collection<Application> getExplorerApplicationsByStatus() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final int id = userAccount.getId();
		return this.applicationRepository.getExplorerApplicationsByStatus(id);
	}

	public Collection<Application> getManagerTripsApplications(
			final int managerId) {
		return this.applicationRepository
				.getManagerTripsApplications(managerId);
	}

	public Double getPendingRatio() {
		return this.applicationRepository.getPendingRatio();
	}

	public Double getDueRatio() {
		return this.applicationRepository.getDueRatio();
	}

	public Double getAcceptedRatio() {
		return this.applicationRepository.getAcceptedRatio();
	}

	public Double getCancelledRatio() {
		return this.applicationRepository.getCancelledRatio();
	}

	// C.13.4 El Explorer puede cancelar una Aplication con status ACCEPTED
	// siempre que la
	// fecha
	// de comienzo no haya pasado
//	public void cancelApplicationAccepted(final Application application) {
//		Assert.notNull(application);
//		Assert.isTrue(application.getId() != 0);
//		// La fecha de comienzo no debe haber pasado
//		final Date currentDate = new Date(System.currentTimeMillis());
//		Assert.isTrue(currentDate.before(application.getTrip().getStartDate()));
//		// Solo si tiene status ACCEPTED
//		Assert.isTrue(application.getStatus().equals("ACCEPTED"));
//		application.setStatus("CANCELLED");
//
//	}

	public Collection<Application> getExplorerApplications(final int explorerId) {
		return this.applicationRepository.getExplorerApplications(explorerId);
	}

	public void changeStatus(final Application a) {
		Assert.notNull(a);
		Manager m = new Manager();

		m = (Manager) this.actorService.findByPrincipal();

		if (a.getStatus().equals("REJECTED"))
			Assert.isTrue(!a.getCancelReason().isEmpty(),"message.error.cancelReason");

		Assert.isTrue(m.equals(this.managerService
				.getManagerFromApplicationId(a.getId())));

		this.applicationRepository.save(a);
		
		Explorer e = explorerService.findByApplicationId(a.getId());
		
		//messageService.sendApplicationNotification(e,m);
	}

	public void cancelApplication(final Application a) {
		Assert.notNull(a);
		Assert.isTrue(a.getStatus().equals("ACCEPTED"));
		this.checkPrincipal(a);
		Trip t;
		final Date currentDate = new Date(System.currentTimeMillis());

		t = this.tripService.getTripByApplicationId(a.getId());
		Assert.isTrue(t.getStartDate().after(currentDate),
				"message.error.cancelApplicationDate");

		a.setStatus("CANCELLED");
		this.applicationRepository.save(a);
		
		Explorer e = explorerService.findByApplicationId(a.getId());
		Manager m = managerService.getManagerFromApplicationId(a.getId());
		
		//messageService.sendApplicationNotification(e,m);
	}

	public void addCreditCard(final Application a, final CreditCard c) {
		Assert.notNull(a);
		Assert.notNull(c);
		this.checkPrincipal(a);

		this.creditCardService.save(c);
		a.setCreditCard(c);
		this.applicationRepository.save(a);
	}

	public Collection<String> getSetOfStatus(final int explorerId) {
		return this.applicationRepository.getSetOfStatus(explorerId);
	}

	public Double[] getAvgMinMaxStdevPerTrip() {
		return this.applicationRepository.computeAvgMinMaxStdvPerTrip();
	}

	public Collection<Application> getApplicationsByStatusAndExplorerId(
			final int explorerId, final String status) {
		return this.applicationRepository.getApplicationsByStatusAndExplorerId(
				explorerId, status);
	}

	// Cuando un trip es cancelado, todas sus aplicattions pasan a ser
	// canceladas

	public void cancelApplications(Trip t) {
		Collection<Application> apps = t.getApplications();

		for (Application a : apps) {
			a.setStatus("CANCELLED");
			a.setCancelReason("Trip has been cancelled");
			applicationRepository.save(a);
		}
	}
}
