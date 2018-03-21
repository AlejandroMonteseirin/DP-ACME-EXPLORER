package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import domain.Auditor;

@Controller
@RequestMapping("/auditor/registration")
public class AuditorRegistrationController {

	// Services
	// -------------------------------------------------------------------
	@Autowired
	private AuditorService auditorService;

	//
	// @Autowired
	// private StoryService storyService;
	//
	// @Autowired
	// private ContactService contactService;

	// Constructors
	// -------------------------------------------------------------------

	public AuditorRegistrationController() {
		super();
	}

	// Creation
	// -------------------------------------------------------------------

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Auditor auditor;

		auditor = this.auditorService.create();
		res = this.createEditModelAndView(auditor);

		return res;
	}

	// Edit-------------------------
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Auditor auditor, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(auditor);
		} else {
			try {
//				String nuevo = actorService.checkTelefono(auditor
//						.getPhoneNumber());
//				auditor.setPhoneNumber(nuevo);
				this.auditorService.save(auditor);
				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				res = this.createEditModelAndView(auditor,
						errorMessage);

			}
		}
		return res;

	}

	// Ancillary methods
	// -------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Auditor auditor) {
		ModelAndView res;

		res = createEditModelAndView(auditor, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Auditor auditor,
			String message) {
		ModelAndView res;

		res = new ModelAndView("auditor/registration");
		res.addObject("auditor", auditor);
		res.addObject("message", message);

		return res;
	}

}
