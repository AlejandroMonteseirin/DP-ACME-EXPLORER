package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import domain.Explorer;

@Controller
@RequestMapping("/explorer/registration")
public class ExplorerRegistrationController {

	// Services
	// -------------------------------------------------------------------
	@Autowired
	private ExplorerService explorerService;

	// Constructors
	// -------------------------------------------------------------------

	public ExplorerRegistrationController() {
		super();
	}

	// Creation
	// -------------------------------------------------------------------

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Explorer explorer;

		explorer = explorerService.create();
		res = createEditModelAndView(explorer);

		return res;
	}

	// Edit-------------------------
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Explorer explorer, BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			res = createEditModelAndView(explorer);

		} else {
			try {
				explorerService.save(explorer);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				res = createEditModelAndView(explorer, errorMessage);

			}

		}
		return res;

	}

	// Ancillary methods
	// -------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Explorer explorer) {
		ModelAndView res;

		res = createEditModelAndView(explorer, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Explorer explorer,
			String message) {
		ModelAndView res;

		res = new ModelAndView("explorer/registration");
		res.addObject("explorer", explorer);
		res.addObject("message", message);

		return res;
	}

}
