package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("/manager/registration")
public class ManagerRegistrationController {

	// Services
	// -------------------------------------------------------------------
	@Autowired
	private ManagerService managerService;


	// Constructors
	// -------------------------------------------------------------------

	public ManagerRegistrationController() {
		super();
	}

	// Creation
	// -------------------------------------------------------------------

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Manager manager;

		manager = this.managerService.create();
		res = this.createEditModelAndView(manager);

		return res;
	}

	// Edit-------------------------
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Manager manager, BindingResult binding) {
		ModelAndView res = null;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(manager);
		} else {
			try {

				this.managerService.save(manager);
				res = new ModelAndView("redirect:/");

			}

			catch (Throwable oops) {

				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}

				res = this.createEditModelAndView(manager, errorMessage);
			}

		}
		return res;
	}

	// Ancillary methods
	// -------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Manager manager) {
		ModelAndView res;

		res = createEditModelAndView(manager, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Manager manager,
			String message) {
		ModelAndView res;

		res = new ModelAndView("manager/registration");
		res.addObject("manager", manager);
		res.addObject("message", message);

		return res;
	}

}
