package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import domain.Ranger;
import services.RangerService;

@Controller
@RequestMapping("/ranger")
public class RangerController extends AbstractController{
	
	//Services -------------------------------------------
		@Autowired
		private RangerService rangerService;
		
		//Listing ---------------------------------------------------
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(){
			ModelAndView result;
			Collection<Ranger> rangers;
			
			rangers = rangerService.findAll();
			
			result = new ModelAndView("ranger/list");
			result.addObject("requestURI", "ranger/list.do");
			result.addObject("rangers", rangers);
			
			return result;
		}
		
		// Display -------------------------------------------------------------------
		
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam int rangerId) {
			ModelAndView result;
			Ranger ranger;
			
			ranger = rangerService.findOne(rangerId);
			result = new ModelAndView("ranger/display");
			result.addObject("ranger", ranger);				
			
			return result;
		}

}
