package controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ValueService;
import domain.Value;

@Controller
@RequestMapping("/value/admin")
public class ValueController extends AbstractController{
	
	// Services ---------------------------------------------------------------

	@Autowired
	private ValueService valueService;
	
	// Constructors -----------------------------------------------------------

	public ValueController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required=true) final Integer tagId) {
		ModelAndView result;
		Value value;

		value = valueService.create(tagId);
		result = this.createEditModelAndView(value);

		return result;
	}
	
	// Listing ----------------------------------------------------------------

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list(@RequestParam(required=false) final Integer tripId,
//			@RequestParam (required=false)Integer ValueId) {
//		ModelAndView result;
//		Collection<Value> Values;
//		Collection<Value> values;
//		Trip t;
//		
//		Values = valueService.findAll();
//		
//		result = new ModelAndView("Value/list");
//		
//		if(tripId != null){
//			t = tripService.findOne(tripId);
//			result.addObject("trip",t);
//		}
//		
//		if(ValueId != null){
//			values = valueService.findOne(ValueId).getValues();
//			result.addObject("values",values);
//			result.addObject("ValueId",ValueId);
//		}
//		
//		result.addObject("requestURI", "Value/list.do");
//		result.addObject("Values",Values);
//
//		return result;
//	}
	
	//Edition -----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int valueId) {
		ModelAndView result;
		Value value;

		value = valueService.findOne(valueId);
		
		result = this.createEditModelAndView(value);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Value value, final BindingResult binding) {
		ModelAndView result;
		Value saved;
		String redirectView;
		
		if (binding.hasErrors())
			result = this.createEditModelAndView(value);
		else
			try {
				saved = this.valueService.save(value);
				redirectView = String.format("redirect:/tag/list.do?tagId=%s", saved.getTag().getId());
				result = new ModelAndView(redirectView);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(value, "application.commit.error");
			}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Value value, final BindingResult binding) {
		ModelAndView result;
	
		try {
			this.valueService.delete(value);
			result = new ModelAndView("redirect:/tag/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(value, "application.commit.error");
		}
	
		return result;
	}

	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Value value) {
		ModelAndView result;

		result = this.createEditModelAndView(value, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Value value, final String message) {
		ModelAndView result;
		
		result = new ModelAndView("value/edit");
		result.addObject("value", value);
		result.addObject("message", message);

		return result;
	}

}
