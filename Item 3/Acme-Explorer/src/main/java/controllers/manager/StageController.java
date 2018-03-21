package controllers.manager;

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
import services.StageService;
import services.TripService;

import controllers.AbstractController;
import domain.Application;
import domain.Auditor;
import domain.Manager;
import domain.Stage;
import domain.Tag;
import domain.Trip;

@Controller
@RequestMapping("/stage")
public class StageController extends AbstractController {
	
	// Services ---------------------------------------------------------------

		@Autowired
		private StageService stageService;
		@Autowired
		private ActorService actorService;
		@Autowired
		private TripService tripService;

		// Constructors -----------------------------------------------------------

		public StageController() {
			super();
		}

		// Listing ----------------------------------------------------------------

		@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
		public ModelAndView listManagerStages() {
			ModelAndView result;
			Collection<Stage> Stages;
			Manager m;

			m = (Manager) actorService.findByPrincipal();

			result = new ModelAndView("Stage/list");
			result.addObject("requestURI", "Stage/manager/list.do");

			return result;
		}
		
		// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int tripId) {
			ModelAndView result;
			Stage stage;

			stage = this.stageService.create(tripId);
			result = this.createEditModelAndView(stage);

			return result;
		}

		//Edition -----------------------------------------------------------------
		
		@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int stageId) {
			ModelAndView result;
			Stage stage;

			stage = stageService.findOne(stageId);
			
			result = this.createEditModelAndView(stage);

			return result;
		}
		
		@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Stage stage, final BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors())
				result = this.createEditModelAndView(stage);
			else
				try {
					this.stageService.save(stage);
					result = new ModelAndView("redirect:/trip/manager/list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(stage, "application.commit.error");
				}

			return result;
		}
		
	@RequestMapping(value = "manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Stage stage, final BindingResult binding) {
		ModelAndView result;

		try {
			this.stageService.delete(stage);
			result = new ModelAndView("redirect:/trip/manager/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(stage, "application.commit.error");
		}

		return result;
	}

		// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(final Stage stage) {
			ModelAndView result;

			result = this.createEditModelAndView(stage, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final Stage stage, final String message) {
			ModelAndView result;
			Collection<Trip> editableTrips;
			
			editableTrips = tripService.getNotPublishedTrips();
			result = new ModelAndView("stage/edit");
			result.addObject("stage", stage);
			result.addObject("trips", editableTrips);
			result.addObject("message", message);

			return result;
		}

}
