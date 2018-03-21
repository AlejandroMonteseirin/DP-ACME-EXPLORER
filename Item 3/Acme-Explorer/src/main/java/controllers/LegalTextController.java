/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.LegalText;

import services.LegalTextService;

@Controller
@RequestMapping("/legalText/admin")
public class LegalTextController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private LegalTextService legalTextService;

	// Constructors -----------------------------------------------------------

	public LegalTextController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		LegalText legalText;

		legalText = legalTextService.create();
		result = createEditModelAndView(legalText);
		
		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Collection<LegalText> legalTexts;

		legalTexts = legalTextService.findAll();

		result = new ModelAndView("legalText/list");
		result.addObject("legalTexts", legalTexts);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalTextId) {
		ModelAndView result;
		LegalText legalText;

		legalText = legalTextService.findOne(legalTextId);
		Assert.isTrue(legalText.getSavedMode().equals("DRAFT MODE"));

		result = this.createEditModelAndView(legalText);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalText legalText,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalText);
		else
			try {
				this.legalTextService.save(legalText);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				String errorMessage = "category.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(legalText, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalText legalText,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.legalTextService.delete(legalText);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			String errorMessage = "category.commit.error";
			
			if(oops.getMessage().contains("message.error")){
				errorMessage = oops.getMessage();
			}
			result = this.createEditModelAndView(legalText, errorMessage);
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final LegalText legalText) {
		ModelAndView result;

		result = this.createEditModelAndView(legalText, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalText legalText,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("legalText/edit");
		result.addObject("legalText", legalText);
		result.addObject("message", message);

		return result;
	}
}
