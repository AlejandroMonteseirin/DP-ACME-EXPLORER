package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SurvivalClassService;
import services.TripService;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("survivalClass/")
public class SurvivalClassController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SurvivalClassService survivalClassService;

	@Autowired
	private ActorService actorService;
	@Autowired
	private TripService tripService;

	// Constructors -----------------------------------------------------------

	public SurvivalClassController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping("explorer/list-registered")
	public ModelAndView listRegistered() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasss;

		survivalClasss = survivalClassService.findRegistered();
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasss);
		result.addObject("registered", true);
		result.addObject("requestURI", "survivalClass/list-registered.do");

		return result;
	}

	@RequestMapping("explorer/list-not-registered")
	public ModelAndView listNotRegistered() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasss;

		survivalClasss = survivalClassService.findNotRegistered();
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasss);
		result.addObject("registered", false);
		result.addObject("requestURI", "survivalClass/list-not-registered.do");

		return result;

	}

	// realmente no son todas si no solo las suyas
	@RequestMapping("manager/list-all")
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClass;

		Manager m = (Manager) actorService.findByPrincipal();
		survivalClass = m.getSurvivalClasses();

		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClass);
		result.addObject("requestURI", "survivalClass/list-all.do");

		return result;
	}

	// Attend and unAttend
	// -----------------------------------------------------------

	@RequestMapping(value = "explorer/attend", method = RequestMethod.GET)
	public ModelAndView attend(@RequestParam int survivalClassId) {
		ModelAndView result;

		try {
			survivalClassService.attend(survivalClassId);
			result = listNotRegistered();
			result.addObject("message", "survivalClass.commit.ok");
		} catch (Throwable oops) {
			result = listNotRegistered();
			result.addObject("message", "survivalClass.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "explorer/unAttend", method = RequestMethod.GET)
	public ModelAndView unregister(@RequestParam int survivalClassId) {
		ModelAndView result;

		try {
			survivalClassService.unAttend(survivalClassId);
			result = listRegistered();
			result.addObject("message", "survivalClass.commit.ok");
		} catch (Throwable oops) {
			result = listRegistered();
			result.addObject("message", "survivalClass.commit.error");
		}

		return result;
	}

	// editar y crear
	@RequestMapping(value = "manager/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = survivalClassService.findOneToEdit(survivalClassId);

		result = this.createEditModelAndView(survivalClass);

		return result;
	}

	@RequestMapping(value = "manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SurvivalClass survivalClass,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClass);
		else
			try {
				this.survivalClassService.save(survivalClass);
				result = new ModelAndView("redirect:list-all.do");
			} catch (final Throwable oops) {
				String errorMessage = "survivalClass.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(survivalClass,
						errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SurvivalClass survivalClass,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.survivalClassService.delete(survivalClass);
			result = new ModelAndView("redirect:list-all.do");
		} catch (final Throwable oops) {
			String errorMessage = "survivalClass.commit.error";

			if (oops.getMessage().contains("message.error")) {
				errorMessage = oops.getMessage();
			}
			result = this.createEditModelAndView(survivalClass, errorMessage);
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final SurvivalClass survivalClass) {
		ModelAndView result;

		result = this.createEditModelAndView(survivalClass, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final SurvivalClass survivalClass, final String message) {
		ModelAndView result;

		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("message", message);
		Collection<Trip> trips;
		trips = tripService.getVisibleTrips();
		result.addObject("trips", trips);
		return result;
	}

	@RequestMapping(value = "manager/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SurvivalClass s;
		s = survivalClassService.create();
		Collection<Trip> trips;

		trips = tripService.getVisibleTrips();
		result = this.createEditModelAndView(s);
		result.addObject("trips", trips);

		return result;
	}

	// display

	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = survivalClassService.findOne(survivalClassId);
		result = new ModelAndView("survivalClass/display");
		result.addObject("survivalClass", survivalClass);

		return result;
	}

}
