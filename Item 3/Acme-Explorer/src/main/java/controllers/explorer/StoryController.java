package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.StoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Controller
@RequestMapping("/story/explorer")
public class StoryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StoryService storyService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private TripService tripService;

	// Constructors -----------------------------------------------------------

	public StoryController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Story story;

		story = this.storyService.create();
		result = this.createEditModelAndView(story);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Story> stories;
		Explorer e;

		e = explorerService.findByPrincipal();
		stories = storyService.getStoriesByExplorerId(e.getId());

		result = new ModelAndView("story/list");
		result.addObject("requestURI", "Story/list.do");
		result.addObject("stories", stories);

		return result;
	}

	// Edition -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int storyId) {
		ModelAndView result;
		Story story;

		story = storyService.findOne(storyId);

		result = this.createEditModelAndView(story);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(story);
		else
			try {
				this.storyService.save(story);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story,
						"story.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Story story, final BindingResult binding) {
		ModelAndView result;

		try {
			this.storyService.delete(story);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(story, "story.commit.error");
		}

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int storyId) {

		ModelAndView result;
		Story r;

		r = storyService.findOne(storyId);

		result = new ModelAndView("story/display");
		result.addObject("story", r);

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Story Story) {
		ModelAndView result;

		result = this.createEditModelAndView(Story, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Story Story,
			final String message) {
		ModelAndView result;
		Collection<Trip> trips;
		Explorer e;

		e = explorerService.findByPrincipal();
		trips = tripService.getAcceptedTrips(e.getId());

		result = new ModelAndView("story/edit");
		result.addObject("story", Story);
		result.addObject("trips", trips);
		result.addObject("message", message);

		return result;
	}

}
