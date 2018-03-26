package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.FinderService;
import services.LegalTextService;
import services.RangerService;
import services.SponsorshipService;
import services.StageService;
import services.TagService;
import services.TripService;
import services.ValueService;
import domain.Category;
import domain.Finder;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Sponsorship;
import domain.Stage;
import domain.Tag;
import domain.Trip;
import domain.Value;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService tripService;
	@Autowired
	private StageService stageService;
	@Autowired
	private LegalTextService legalTextService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RangerService rangerService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private FinderService finderService;
	@Autowired
	private SponsorshipService sponsorshipService;
	@Autowired
	private ValueService valueService;

	// Constructors -----------------------------------------------------------

	public TripController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(required = false) final Integer categoryId,
			@RequestParam(required = false) Integer pageNumber,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView result;
		Collection<Trip> visibleTrips;
		final Collection<Trip> result2 = new ArrayList<Trip>();
		Collection<Category> childCategories = new ArrayList<>();
		Double totalPages = 0.;

		if (pageNumber == null)
			pageNumber = 1;
		if (pageSize == null)
			pageSize = 5;

		if (categoryId == null) {
			totalPages = Math
					.ceil((tripService.getVisibleTrips().size() / (double) pageSize));
			visibleTrips = this.tripService.paginatedTripsSearch(pageNumber,
					pageSize).getContent();
		} else {
			visibleTrips = this.tripService.showAllTripsByCategory(categoryId,
					result2);
			childCategories = this.categoryService
					.getChildCategories(categoryId);
		}

		result = new ModelAndView("trip/list");
		result.addObject("requestURI", "trip/list.do");
		result.addObject("visibleTrips", visibleTrips);
		result.addObject("childCategories", childCategories);
		result.addObject("pageNumber", pageNumber);
		result.addObject("pageSize", pageSize);
		result.addObject("totalPages", totalPages);

		return result;
	}

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView listManager(
			@RequestParam(required = false) Integer pageNumber,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView result;
		Collection<Trip> visibleTrips;
		Manager m;
		Double totalPages = 0.;

		if (pageNumber == null || pageNumber == 0)
			pageNumber = 1;
		if (pageSize == null)
			pageSize = 5;

		Boolean isManager = true;
		m = (Manager) this.actorService.findByPrincipal();
		visibleTrips = this.tripService.getTripsByManagerIdPageable(m.getId(),
				pageNumber, pageSize).getContent();
		totalPages = Math.ceil((tripService.getTripsByManagerId(m.getId())
				.size() / (double) pageSize));

		result = new ModelAndView("trip/list");
		result.addObject("requestURI", "trip/manager/list.do");
		result.addObject("visibleTrips", visibleTrips);
		result.addObject("pageNumber", pageNumber);
		result.addObject("pageSize", pageSize);
		result.addObject("totalPages", totalPages);
		result.addObject("isManager",isManager);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.create();
		result = this.createEditModelAndView(trip);

		return result;
	}

	// Edition
	// --------------------------------------------------------------------

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.findOneToEdit(tripId);

		result = this.createEditModelAndView(trip);

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(trip);
		else
			try {
				if (trip.getId() != 0)
					this.tripService.save(trip);
				else
					this.tripService.saveFromCreate(trip);
				result = new ModelAndView("redirect:/trip/manager/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "application.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(trip, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {
		ModelAndView result;

		try {
			this.tripService.delete(trip);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(trip,
					"application.commit.error");
		}

		return result;
	}

	// Display
	// -------------------------------------------------------------------

	@RequestMapping(value = "/manager/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		Manager m;
		Collection<Trip> managerTrips;
		Boolean ownTrip = false;
		Boolean isPublished;
		Sponsorship s;

		m = (Manager) this.actorService.findByPrincipal();
		managerTrips = this.tripService.getTripsByManagerId(m.getId());
		trip = this.tripService.findOne(tripId);
		isPublished = tripService.checkTripIsPublished(trip);

		if (managerTrips.contains(trip))
			ownTrip = true;

		result = new ModelAndView("trip/display");
		result.addObject("trip", trip);
		result.addObject("ownTrip", ownTrip);
		result.addObject("isPublished", isPublished);

		if (sponsorshipService.findAll().size() != 0) {
			s = sponsorshipService.randomSponsorship();
			result.addObject("sponsorship", s);
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayAll(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		Sponsorship s;

		trip = this.tripService.findOne(tripId);

		result = new ModelAndView("trip/display");
		result.addObject("trip", trip);

		if (sponsorshipService.findAll().size() != 0) {
			s = sponsorshipService.randomSponsorship();
			result.addObject("sponsorship", s);
		}

		return result;
	}

	// Finder search
	// ----------------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchfortrips")
	public ModelAndView searchFinder(@Valid final Finder finder,
			final BindingResult binding) {
		ModelAndView result;
		Collection<Trip> visibleTrips;

		if (finderService.checkSearchIsInCache(finder)) {
			visibleTrips = finder.getTrips();
		} else {
			visibleTrips = this.tripService.findTripsBySearchCriteria(
					finder.getMinPrice(), finder.getMaxPrice(),
					finder.getStartDate(), finder.getEndDate(),
					finder.getKeyWord());
			finderService.saveNewResults(finder, visibleTrips);
		}

		// Guardamos para actualizar la hora de la última búsqueda
		if (finderService.findOne(finder.getId()).getVersion() == finder
				.getVersion()) {
			finderService.save(finder);
		}

		result = new ModelAndView("trip/list");
		result.addObject("requestURI", "trip/list.do");
		result.addObject("visibleTrips", visibleTrips);
		result.addObject("fromFinder", true);

		return result;
	}

	@RequestMapping(value = "/searchByKeywordForm")
	public ModelAndView searchByKeywordForm() {
		ModelAndView result;

		result = new ModelAndView("trip/searchByKeyword");

		return result;
	}

	@RequestMapping(value = "/searchByKeyword", method = RequestMethod.GET)
	public ModelAndView searchByKeyword(
			@RequestParam(value = "keyword", required = true) final String keyword) {
		ModelAndView result;
		Collection<Trip> visibleTrips;

		visibleTrips = this.tripService.tripsByKeyWord(keyword);

		result = new ModelAndView("trip/list");
		result.addObject("requestURI", "trip/list.do");
		result.addObject("visibleTrips", visibleTrips);

		return result;
	}

	@RequestMapping(value = "/searchForm")
	public ModelAndView search() {
		ModelAndView result;

		result = new ModelAndView("trip/listAJAX");
		result.addObject("visibleTrips", tripService.getVisibleTrips());
		return result;
	}

	@RequestMapping(value = "/searchAJAX")
	@ResponseBody
	public Collection<Trip> searchAJAX(@RequestParam final String keyword) {

		Collection<Trip> res = new ArrayList<>();
		for (Trip t : this.tripService.tripsByKeyWord(keyword)) {
			Trip t2 = t;
			t2.setApplications(null);
			t2.setStages(null);
			t2.setSponsorships(null);
			t2.setAudits(null);
			t2.setLegalText(null);
			t2.setNotes(null);
			t2.setRanger(null);
			t2.setCategory(null);
			t2.setValues(null);
			res.add(t2);
		}

		return res;
	}

	// @RequestMapping(value = "/searchAJAX",produces = "application/json")
	// public @ResponseBody Collection<Trip> trips(@RequestParam(value = "key",
	// required = false) final String key) {
	// List<String> names = new ArrayList<>();
	// Collection<Trip> res = new ArrayList<>();
	//
	// for(Trip t : this.tripService.tripsByKeyWord(key)){
	// Trip t2 = t;
	// t2.setApplications(null);
	// t2.setStages(null);
	// t2.setSponsorships(null);
	// t2.setAudits(null);
	// t2.setLegalText(null);
	// t2.setNotes(null);
	// t2.setRanger(null);
	// t2.setCategory(null);
	// t2.setValues(null);
	// res.add(t2);
	// }
	//
	// return res;
	// }

	// Add/Remove tag values
	// ----------------------------------------------------------

	@RequestMapping(value = "/addValue", method = RequestMethod.GET)
	public ModelAndView addValue(
			@RequestParam(required = true) final int valueId,
			@RequestParam(required = true) final int tripId,
			@RequestParam(required = true) final int tagId) {
		ModelAndView result;
		Collection<Value> values;
		Collection<Tag> tags;
		Trip t;

		values = this.valueService.getValuesByTagId(tagId);
		tags = this.tagService.findAll();
		t = this.tripService.findOne(tripId);

		this.tripService.addValue(tripId, valueId);

		result = new ModelAndView("tag/list");
		result.addObject("values", values);
		result.addObject("tagId", tagId);
		result.addObject("tags", tags);
		result.addObject("trip", t);

		return result;
	}

	@RequestMapping(value = "/removeValue", method = RequestMethod.GET)
	public ModelAndView removeValue(
			@RequestParam(required = true) final Integer valueId,
			@RequestParam(required = true) final Integer tripId,
			@RequestParam(required = true) final Integer tagId) {
		ModelAndView result;
		Collection<Value> values;
		Collection<Tag> tags;
		Trip t;

		values = this.valueService.getValuesByTagId(tagId);
		tags = this.tagService.findAll();
		t = this.tripService.findOne(tripId);

		this.tripService.removeValue(tripId, valueId);

		result = new ModelAndView("tag/list");
		result.addObject("values", values);
		result.addObject("tagId", tagId);
		result.addObject("tags", tags);
		result.addObject("trip", t);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createEditModelAndView(trip, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip,
			final String message) {
		ModelAndView result;

		Collection<Stage> stages;
		Collection<LegalText> legalTexts;
		Collection<Category> categories;
		Collection<Ranger> rangers;
		Collection<Tag> tags;

		stages = this.stageService.findAll();
		legalTexts = this.legalTextService.getFinalModeLegalTexts();
		categories = this.categoryService.getAllCategories();
		rangers = this.rangerService.findAll();
		tags = this.tagService.findAll();
		result = new ModelAndView("trip/edit");

		result.addObject("trip", trip);
		result.addObject("message", message);
		result.addObject("stages", stages);
		result.addObject("categories", categories);
		result.addObject("rangers", rangers);
		result.addObject("legalTexts", legalTexts);
		result.addObject("tags", tags);

		return result;
	}

}
