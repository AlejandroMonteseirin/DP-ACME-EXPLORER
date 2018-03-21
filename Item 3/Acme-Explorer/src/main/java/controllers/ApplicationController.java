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
import services.ApplicationService;
import services.ExplorerService;
import services.ManagerService;
import services.MessageService;
import services.TripService;
import domain.Application;
import domain.Explorer;
import domain.Manager;
import domain.Trip;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController{
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private TripService tripService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private ManagerService managerService;
	
	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/explorer/list", method = RequestMethod.GET)
	public ModelAndView listExplorer(@RequestParam (required=false)String statusSelection) {
		ModelAndView result;
		Collection<Application> applications;
		Explorer e;
		Collection<String> statusSet;
		
		e = (Explorer) actorService.findByPrincipal();
		applications = applicationService.getExplorerApplications(e.getId());
		statusSet = applicationService.getSetOfStatus(e.getId());
		
		if(statusSelection!=null){
			applications = applicationService.getApplicationsByStatusAndExplorerId(e.getId(), statusSelection);
		}

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/explorer/list.do");
		result.addObject("applications", applications);
		result.addObject("statusSet", statusSet);

		return result;
	}
	
	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView listManager() {
		ModelAndView result;
		Collection<Application> applications;
		Manager m;
		
		m = (Manager) actorService.findByPrincipal();
		applications = applicationService.getManagerTripsApplications(m.getId());

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/manager/list.do");
		result.addObject("applications", applications);

		return result;
	}
	

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/explorer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam (required = false) final Integer tripId) {
		ModelAndView result;
		Application application;
		Collection<Trip> trips;
		Explorer e;
		
		e = (Explorer) actorService.findByPrincipal();
		trips = tripService.getWithoutApplicationTrips(e.getId());
		application = this.applicationService.create();
		result = this.createEditModelAndView(application);
		result.addObject("trips",trips);
		

		if(tripId!=null){
			result.addObject("tripId",tripId);
		}

		return result;
	}
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = applicationService.findOne(applicationId);
		
		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				Application saved = this.applicationService.save(application);
				Explorer explorer = explorerService.findByApplicationId(saved.getId());
				Manager manager = managerService.getManagerFromApplicationId(saved.getId());
				messageService.sendApplicationNotification(explorer, manager);
				result = new ModelAndView("redirect:explorer/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "application.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(application, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Application application, final BindingResult binding) {
		ModelAndView result;

		try {
			this.applicationService.delete(application);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(application, "application.commit.error");
		}

		return result;
	}

	
	// Status change ----------------------------------------------------------------
	
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "changeStatus")
	public ModelAndView changeStatus(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				applicationService.changeStatus(application);
				Explorer explorer = explorerService.findByApplicationId(application.getId());
				Manager manager = managerService.getManagerFromApplicationId(application.getId());
				messageService.sendApplicationNotification(explorer, manager);
				
				result = new ModelAndView("redirect:manager/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "application.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(application, errorMessage);
			}

		return result;
	}
	
	@RequestMapping(value = "explorer/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int applicationId) {
		ModelAndView result;
		Application a;
		
		a = applicationService.findOne(applicationId);
		
			applicationService.cancelApplication(a);
			Explorer explorer = explorerService.findByApplicationId(a.getId());
			Manager manager = managerService.getManagerFromApplicationId(a.getId());
			messageService.sendApplicationNotification(explorer, manager);
			result = new ModelAndView("redirect:explorer/list.do");
		
		return result;
	}
	
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {
		ModelAndView result;


		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", message);

		return result;
	}

}
