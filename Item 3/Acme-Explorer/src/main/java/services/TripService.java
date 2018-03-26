package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TripRepository;
import domain.Application;
import domain.Audit;
import domain.Category;
import domain.Manager;
import domain.Note;
import domain.Sponsorship;
import domain.Stage;
import domain.Trip;
import domain.Value;

@Service
@Transactional
public class TripService {

	// MANAGED REPOSITORY ---------------

	@Autowired
	private TripRepository tripRepository;

	// SUPPORTING SERVICES -------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private LegalTextService legalTextService;
	@Autowired
	private ValueService valueService;
	@Autowired
	private StageService stageService;
	@Autowired
	private RangerService rangerService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ApplicationService applicationService;

	// CONSTRUCTOR ---------------

	public TripService() {
		super();
	}

	// SIMPLE CRUD METHODS -----------

	public Trip create() {
		Trip t;
		Collection<Application> apps;
		Collection<Audit> audits;
		Collection<Note> notes;
		Collection<Sponsorship> sponsorships;
		Collection<Stage> stages;
		Collection<Value> values;

		t = new Trip();
		apps = new ArrayList<>();
		audits = new ArrayList<>();
		notes = new ArrayList<>();
		sponsorships = new ArrayList<>();
		stages = new ArrayList<>();
		values = new ArrayList<>();

		t.setApplications(apps);
		t.setAudits(audits);
		t.setStatus("ACTIVE");
		t.setNotes(notes);
		t.setSponsorships(sponsorships);
		t.setStages(stages);
		t.setTicker(this.getTicker());
		t.setValues(values);

		return t;
	}

	public Trip saveFromCreate(final Trip trip) {
		Assert.notNull(trip);
		Assert.isTrue(trip.getPublicationDate().after(
				new Date(System.currentTimeMillis() - 24 * 3600 * 1000l)),"message.error.publicationDate");
		Assert.isTrue(trip.getStartDate().after(
				new Date(System.currentTimeMillis())),"message.error.startDate");
		Assert.isTrue(trip.getEndDate().after(
				new Date(System.currentTimeMillis())),"message.error.endDate");
		Assert.isTrue(trip.getPublicationDate().before(trip.getStartDate()),"message.error.startDatePubliDate");
		Assert.isTrue(trip.getPublicationDate().before(trip.getEndDate()),"message.error.endDatePubliDate");
		Assert.isTrue(trip.getStartDate().before(trip.getEndDate()),"message.error.startDateEndDate");
		
		Trip result;
		Manager m;
		Collection<Trip> trips;

		m = (Manager) this.actorService.findByPrincipal();
		trips = m.getTrips();

		result = this.tripRepository.save(trip);
		trips.add(result);
		m.setTrips(trips);
		this.managerService.save(m);

		if (trip.getLegalText() != null) {
			Collection<Trip> ltTrips;
			ltTrips = trip.getLegalText().getTrips();
			ltTrips.add(result);
			trip.getLegalText().setTrips(ltTrips);

			this.legalTextService.save(trip.getLegalText());
		}

		final Collection<Trip> rangerTrips = trip.getRanger().getTrips();
		rangerTrips.add(result);
		trip.getRanger().setTrips(rangerTrips);
		this.rangerService.save(trip.getRanger());

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(trip.getTitle());
		this.administratorService.checkIsSpam(trip.getDescription());
		this.administratorService.checkIsSpam(trip.getExplorerRequirements());
		this.administratorService.checkIsSpam(trip.getCancelReason());

		return result;
	}

	public Trip save(final Trip trip) {
		Assert.notNull(trip);
		Assert.isTrue(trip.getPublicationDate().after(
				new Date(System.currentTimeMillis() - 24 * 3600 * 1000l)));
		Assert.isTrue(trip.getStartDate().after(
				new Date(System.currentTimeMillis())));
		Assert.isTrue(trip.getEndDate().after(
				new Date(System.currentTimeMillis())));
		Assert.isTrue(trip.getPublicationDate().before(trip.getStartDate()));
		Assert.isTrue(trip.getPublicationDate().before(trip.getEndDate()));
		Assert.isTrue(trip.getStartDate().before(trip.getEndDate()));
		if (trip.getStatus().equals("CANCELLED")) {
			Assert.isTrue(!trip.getCancelReason().isEmpty(),
					"message.error.cancelReason");
		}

		Trip result;

		if (trip.getStages().size() > 0)
			trip.setPrice(this.getTripPrice(trip));

		result = this.tripRepository.save(trip);

		if (result.getStatus().equals("CANCELLED")
				&& result.getApplications() != null)
			this.applicationService.cancelApplications(trip);

		return result;

	}

	// C.10.2
	public Collection<Trip> findAll() {
		Collection<Trip> result;

		result = this.tripRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// C.10.2
	public Trip findOne(final int tripId) {
		Trip result;

		result = this.tripRepository.findOne(tripId);

		return result;
	}

	public Trip findOneToEdit(final int tripId) {
		Trip result;

		result = this.tripRepository.findOne(tripId);

		this.checkPrincipal(result);

		return result;
	}

	// C.12.1 Los que pueden borrar sus trips son los managers
	// El trip no debe haber sido publicado
	public void delete(final Trip trip) {
		Assert.notNull(trip);
		Assert.isTrue(trip.getId() != 0);
		Assert.isTrue(trip.getPublicationDate().after(
				new Date(System.currentTimeMillis())));

		// Eliminamos los stages del trip antes de eliminarlo
		for (final Stage s : new ArrayList<>(trip.getStages())){
			trip.getStages().remove(s);
			this.stageService.delete(s);
		}
		trip.getLegalText().getTrips().remove(trip);
		trip.getRanger().getTrips().remove(trip);
		
		Manager m = (Manager)actorService.findByPrincipal();
		m.getTrips().remove(trip);
		
		this.tripRepository.delete(trip);
	}

	// OTHER BUSSINES METHODS ----------------------------

	// B.16.4 Un Admin puede mostrar en el dashboard el ratio de trips con audit
	public Double getAuditedTrips() {
		return this.tripRepository.getAuditedTrips();
	}

	// Tickers se generan automaticamente, con el pattern: YYMMDD-WWWW W es
	// una letra en mayuscula
	public String getTicker() {
		String res = "";
		final Calendar c = Calendar.getInstance();

		String day = Integer.toString(c.get(Calendar.DATE));
		if (day.length() == 1)
			day = "0" + day;
		String month = Integer.toString(c.get(Calendar.MONTH) + 1); // +1 ya que
																	// Enero es
																	// 0
		if (month.length() == 1)
			month = "0" + month;
		String year = Integer.toString(c.get(Calendar.YEAR));
		// Por el patron solo necesitamos las ultimas 2 cifras del anho
		year = year.substring(2, 4);
		// Aqui sacamos las 4 letras mayusculas
		final Random r = new Random();
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final int index1 = r.nextInt(alphabet.length());
		final int index2 = r.nextInt(alphabet.length());
		final int index3 = r.nextInt(alphabet.length());
		final int index4 = r.nextInt(alphabet.length());

		res = year + month + day + "-" + alphabet.charAt(index1)
				+ alphabet.charAt(index2) + alphabet.charAt(index3)
				+ alphabet.charAt(index4);

		// Comprobamos que el ticker sea único
		// Si existe algún ticker igual, volvemos a calcularlo

		for (final Trip t : this.tripRepository.findAll())
			if (t.getTicker().equals(res))
				res = this.getTicker();
		return res;
	}

	public Integer getAcceptedApplicationsByExplorerId(final int explorerId,
			final int tripId) {
		return this.tripRepository.getAcceptedApplicationsByExplorerId(
				explorerId, tripId);
	}

	public Collection<Trip> getAllTrips() {
		return this.tripRepository.getAllTrips();
	}

	public Collection<Trip> getTripsByCategory(final int categoryId) {
		return this.tripRepository.getTripsByCategory(categoryId);
	}

	// C.10.3 --> Buscar trips por palabra clave, que debe estar contenida en
	// sus tickers, titles o descriptions. --> No auth
	public Collection<Trip> tripsByKeyWord(final String keyWord) {
		Assert.notNull(keyWord);
		Collection<Trip> res;
		res = this.tripRepository.getTripsByKeyWord(keyWord);
		return res;
	}

	// C.14.6 --> Un Administrador debe mostrar estas estadisticas por el
	// dashboard

	// The average, the minimum, the maximum, and the standard deviation of the
	// number of trips managed per manager.
	public Double[] tripsPerManagerStats() {
		return this.tripRepository.computeAvgMinMaxStdvPerManager();
	}

	// The average, the minimum, the maximum, and the standard deviation of the
	// price of the trips.
	public Double[] tripsPriceStats() {
		return this.tripRepository.computeAvgMinMaxStdvPerPrice();
	}

	// The average, the minimum, the maximum, and the standard deviation of the
	// number trips guided per ranger.
	public Double[] tripsPerRangerStats() {
		return this.tripRepository.computeAvgMaxMinStdvPerRanger();
	}

	// The ratio of trips that have been cancelled versus the total number of
	// trips that have been organised.
	public Double ratioTripsCancelled() {
		return this.tripRepository.ratioTripsCancelled();
	}

	// The listing of trips that have got at least 10% more applications than
	// the average, ordered by number of applications.
	public Collection<Trip> tripsMoreAplications() {
		return this.tripRepository.tripsMoreAplications();
	}

	// Aqui terminan los metodos para el dashboard

	// C.10.4 Devolver lista de trips dada una categoría(explorar la lista de
	// trips navegando por el árbol de categories) --> No auth
	public Collection<Trip> tripsByCategory(final Category category) {
		return this.tripRepository.getTripsByCategory(category.getId());
	}

	// C.12.3 Un Manager puede cancelar un trip que haya sido publicado pero no
	// haya comenzado
	public void cancelTripNotStarted(final Trip trip) {
		Assert.notNull(trip);
		Assert.isTrue(trip.getId() != 0);
		Assert.isTrue(trip.getPublicationDate().before(
				new Date(System.currentTimeMillis())));
		Assert.isTrue(trip.getStartDate().after(
				new Date(System.currentTimeMillis())));

		trip.setStatus("CANCELLED");
	}

	// C.3 Algunos trips podrán ser cancelados después de la fecha de
	// publicación,
	// en cuyo caso el sistema deberá almacenar el motivo.
	public void cancelTripAferPublication(final Trip trip,
			final String cancelReason) {
		Assert.notNull(trip);
		Assert.isTrue(trip.getId() != 0);
		Assert.isTrue(trip.getPublicationDate().before(
				new Date(System.currentTimeMillis())));
		trip.setStatus("CANCELLED");
		trip.setCancelReason(cancelReason);
	}

	// ENTENDEMOS QUE EL ENUNCIADO ESTA MAL Y SE REFIERE A UNA APPLICATION, ESTA
	// EN APPLICATIONSERVICE
	// C.13.4 El Explorer puede cancelar trip con status ACCEPTED siempre que la
	// fecha
	// de comienzo no haya pasado
	// public void cancelTripAccepted(final Trip trip) {
	// Assert.notNull(trip);
	// Assert.isTrue(trip.getId() != 0);
	// // La fecha de comienzo no debe haber pasado
	// Date currentDate = new Date(System.currentTimeMillis());
	// Assert.isTrue(currentDate.before(trip.getStartDate()));
	// // Sacamaos la application que esta relacionada con nustro trip
	// final Application app = this.applicationService.create(trip);
	// // Solo si tiene status ACCEPTED
	// Assert.isTrue(app.getStatus().equals("ACCEPTED"));
	//
	// }

	public double getTripPrice(final Trip t) {
		Assert.notNull(t);

		double stagesPrice;
		double VATTax;
		double result;

		stagesPrice = 0.0;

		for (final Stage s : t.getStages())
			stagesPrice += s.getPrice();

		VATTax = this.configurationService.getTax();
		result = stagesPrice + stagesPrice * (VATTax / 100);

		return result;
	}

	public Collection<Trip> findTripsBySearchCriteria(Double minPrice,
			Double maxPrice, Date startDate, Date endDate, String keyword) {

		Collection<Trip> trips;

		if (minPrice == null)
			minPrice = 0.;

		if (maxPrice == null)
			maxPrice = 999999.;

		if (startDate == null) {
			startDate = new Date();
			final String startDateString = "06/06/1000";
			final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try {
				startDate = df.parse(startDateString);
			} catch (final ParseException e) {
				e.printStackTrace();
			}
		}

		if (endDate == null) {
			endDate = new Date();
			final String endDateString = "06/06/3000";
			final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try {
				endDate = df.parse(endDateString);
			} catch (final ParseException e) {
				e.printStackTrace();
			}
		}

		if (keyword == null)
			keyword = "";

		trips = this.tripRepository.findTripsBySearchCriteria(minPrice,
				maxPrice, startDate, endDate, keyword);
		List<Trip> tripsSet = new ArrayList<Trip>(trips);

		// Si el número de resultados el mayor del indicado en Configuration,
		// creamos
		// una sublista con ese número de resultados
		if (trips.size() > this.configurationService.getConfiguration()
				.getFinderReturn())
			tripsSet = tripsSet.subList(0, this.configurationService
					.getConfiguration().getFinderReturn());
		return tripsSet;
	}

	public Collection<Trip> getEndedTrips() {
		return this.tripRepository.getEndedTrips();

	}

	public Collection<Trip> getVisibleTrips() {
		return this.tripRepository.getVisibleTrips();
	}

	public Collection<Trip> getVisibleAndActiveTrips() {
		return this.tripRepository.getVisibleAndActiveTrips();
	}

	// A+ Paginated repositories
	public Page<Trip> paginatedTripsSearch(final Integer pageNumber,
			final Integer pageSize) {
		final PageRequest request = new PageRequest(pageNumber - 1, pageSize);
		return this.tripRepository.getVisibleTripsPaginate(request);
	}

	public Collection<Trip> getVisibleTripsByCategory(final int categoryId) {
		return this.tripRepository.getVisibleTripsByCategory(categoryId);
	}

	public Collection<Trip> showAllTripsByCategory(final int categoryId,
			final Collection<Trip> result) {

		Category category;

		category = this.categoryService.findOne(categoryId);

		if (!category.getChildCategories().isEmpty())
			for (final Category c : category.getChildCategories())
				this.showAllTripsByCategory(c.getId(), result);

		result.addAll(this.getVisibleTripsByCategory(categoryId));

		return result;
	}

	public Trip getTripByApplicationId(final int applicationId) {
		return this.tripRepository.getTripByApplicationId(applicationId);
	}

	public Collection<Trip> getTripsByManagerId(final int managerId) {
		return this.tripRepository.getTripsByManagerId(managerId);
	}

	public Page<Trip> getTripsByManagerIdPageable(final int managerId,
			final Integer pageNumber, final Integer pageSize) {
		final PageRequest request = new PageRequest(pageNumber - 1, pageSize);
		return this.tripRepository.getTripsByManagerIdPageable(request,
				managerId);
	}

	public Collection<Trip> getNotPublishedTrips() {
		return this.tripRepository.getNotPublishedTrips();
	}

	public Collection<Trip> getExplorerApplicationTrips(final int explorerId) {
		return this.tripRepository.getExplorerApplicationTrips(explorerId);
	}

	public Collection<Trip> getWithoutApplicationTrips(final int explorerId) {
		Collection<Trip> explorerTrips;
		Collection<Trip> allTrips;

		explorerTrips = this.tripRepository
				.getExplorerApplicationTrips(explorerId);
		allTrips = this.tripRepository.getVisibleTrips();

		allTrips.removeAll(explorerTrips);

		return allTrips;
	}

	public void addValue(final int tripId, final int valueId) {
		final Trip t = this.tripRepository.findOne(tripId);
		final Value v = this.valueService.findOne(valueId);

		final Collection<Value> tripValues = t.getValues();
		tripValues.add(v);
		t.setValues(tripValues);

		this.save(t);
	}

	public void removeValue(final int tripId, final int valueId) {
		final Trip t = this.tripRepository.findOne(tripId);
		final Value v = this.valueService.findOne(valueId);

		final Collection<Value> tripValues = t.getValues();
		tripValues.remove(v);
		t.setValues(tripValues);

		this.save(t);
	}

	public Collection<Trip> getTripsByValue(final int valueId) {
		return this.tripRepository.getTripsByValue(valueId);
	}

	public Boolean checkTripIsPublished(final Trip trip) {
		Boolean isPublished = false;

		if (trip.getPublicationDate().before(
				new Date(System.currentTimeMillis())))
			isPublished = true;

		return isPublished;
	}

	public Collection<Trip> getAcceptedTrips(final int explorerId) {
		return this.tripRepository.getAcceptedTrips(explorerId);
	}

	public Collection<Trip> getTripsExplorerApplication(final int explorerId) {
		return this.tripRepository.getTripsExplorerApplication(explorerId);
	}

	public void checkPrincipal(final Trip t) {
		Manager m;

		if (t.getStatus().equals("CANCELLED"))
			Assert.isTrue(false);

		m = (Manager) this.actorService.findByPrincipal();

		Assert.isTrue(m.getTrips().contains(t));
	}
}
