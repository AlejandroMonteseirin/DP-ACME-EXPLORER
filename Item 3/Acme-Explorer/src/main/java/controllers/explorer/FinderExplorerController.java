package controllers.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FinderService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Finder;

@Controller
@RequestMapping("/finder/explorer")
public class FinderExplorerController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private FinderService finderService;
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public FinderExplorerController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Finder finder;
		Explorer e;
	
		e = (Explorer) actorService.findByPrincipal();
		finder = finderService.getFinderByExplorerId(e.getId());
		result = createEditModelAndView(finder);
	
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Finder finder) {
		ModelAndView result;

		result = createEditModelAndView(finder, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Finder finder, String message) {
		ModelAndView result;
		
				
		result = new ModelAndView("finder/explorer/search");
		result.addObject("finder", finder);
		result.addObject("message", message);
		
		return result;
	}
			
}
