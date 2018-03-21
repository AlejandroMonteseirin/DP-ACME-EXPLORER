package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RangerService;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/registration")
public class RangerRegistrationController {

	// Services
	// -------------------------------------------------------------------
	@Autowired
	private RangerService rangerService;

	// @Autowired
	// private ApplicationService applicationService;
	//
	// @Autowired
	// private StoryService storyService;
	//
	// @Autowired
	// private ContactService contactService;

	// Constructors
	// -------------------------------------------------------------------

	public RangerRegistrationController() {
		super();
	}

	// Creation
	// -------------------------------------------------------------------

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Ranger ranger;

		ranger = this.rangerService.create();
		res = this.createEditModelAndView(ranger);

		return res;
	}

	// Edit-------------------------
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Ranger ranger, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(ranger);
		} else {
			try {
				// String nuevo = actorService.checkTelefono(ranger
				// .getPhoneNumber());
				// ranger.setPhoneNumber(nuevo);
				this.rangerService.save(ranger);
				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				
				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				res = this
						.createEditModelAndView(ranger, errorMessage);

			}
		}
		return res;

	}

	// Ancillary methods
	// -------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Ranger ranger) {
		ModelAndView res;

		res = createEditModelAndView(ranger, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Ranger ranger, String message) {
		ModelAndView res;

		res = new ModelAndView("ranger/registration");
		res.addObject("ranger", ranger);
		res.addObject("message", message);

		return res;
	}

}
