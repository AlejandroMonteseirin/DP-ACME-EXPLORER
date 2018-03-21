/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;

@Controller
@ControllerAdvice
public class AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private AdministratorService administratorService;

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}
	
	@ModelAttribute
	public void banner(Model model) {
		String bannerURL;

		bannerURL = configurationService.getBannerURL();


		model.addAttribute("bannerURL",bannerURL);
	}
	
	@ModelAttribute
	public void pagination(Model model,
			@RequestParam(required=false) Integer pageNumber,
			@RequestParam(required=false) Integer pageSize) {

		if(pageNumber == null){
			pageNumber = 0;
		}
		if(pageSize == null){
			pageSize = 5;
		}

		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
	}
	
	public void checkIsSpam(String s){
		administratorService.checkIsSpam(s);
	}

}
