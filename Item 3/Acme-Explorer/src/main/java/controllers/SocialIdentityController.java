package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialIdentityService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController {

	// Services ----------------------------------------------
	@Autowired
	private SocialIdentityService socialIdentityService;
	@Autowired
	private ActorService actorService;

	// Constructors ----------------------------------------------

	public SocialIdentityController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = socialIdentityService.create();
		result = createEditModelAndView(socialIdentity);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int socialIdentityId) {
		ModelAndView result;

		SocialIdentity socialIdentity = socialIdentityService
				.findOneToEdit(socialIdentityId);
		Assert.notNull(socialIdentity);

		result = createEditModelAndView(socialIdentity);
		Actor actor = actorService.findByPrincipal();
		result.addObject(actor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SocialIdentity socialIdentity,
			BindingResult binding) {
		ModelAndView result;
		String type;
		
		type = actorService.getType(actorService.findByPrincipal().getUserAccount()).toLowerCase();
		result = new ModelAndView("redirect:/actor/" + type + "/edit.do");

		if (binding.hasErrors()) {
			result = createEditModelAndView(socialIdentity);
		} else {
			try {
				socialIdentityService.save(socialIdentity);
				socialIdentityService.addToPrincipal(socialIdentity);
			} catch (Throwable oops) {
				result = createEditModelAndView(socialIdentity,
						"socialIdentity.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SocialIdentity socialIdentity,
			BindingResult binding) {
		ModelAndView result;
		String type;
		
		type = actorService.getType(actorService.findByPrincipal().getUserAccount()).toLowerCase();
		result = new ModelAndView("redirect:/actor/" + type + "/edit.do");

		try {
			socialIdentityService.delete(socialIdentity);
		} catch (Throwable oops) {
			result = createEditModelAndView(socialIdentity,
					"socialIdentity.commit.error");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private ModelAndView createEditModelAndView(SocialIdentity socialIdentity) {
		ModelAndView result;

		result = createEditModelAndView(socialIdentity, null);

		return result;
	}

	private ModelAndView createEditModelAndView(SocialIdentity socialIdentity,
			String message) {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);

		return result;
	}

}
