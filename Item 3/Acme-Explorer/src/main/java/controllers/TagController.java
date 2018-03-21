package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import services.TripService;
import services.ValueService;
import domain.Application;
import domain.Stage;
import domain.Tag;
import domain.Trip;
import domain.Value;

@Controller
@RequestMapping("/tag")
public class TagController extends AbstractController{
	
	// Services ---------------------------------------------------------------

	@Autowired
	private TagService tagService;
	@Autowired
	private TripService tripService;
	@Autowired
	private ValueService		valueService;
	
	// Constructors -----------------------------------------------------------

	public TagController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/admin/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tag tag;

		tag = tagService.create();
		result = this.createEditModelAndView(tag);

		return result;
	}
	
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false) final Integer tripId,
			@RequestParam (required=false)Integer tagId) {
		ModelAndView result;
		Collection<Tag> tags;
		Collection<Value> values;
		Trip t;
		
		tags = tagService.findAll();
		
		result = new ModelAndView("tag/list");
		
		if(tripId != null){
			t = tripService.findOne(tripId);
			result.addObject("trip",t);
		}
		
		if(tagId != null){
			values = this.valueService.getValuesByTagId(tagId);
			result.addObject("values",values);
			result.addObject("tagId",tagId);
		}
		
		result.addObject("requestURI", "tag/list.do");
		result.addObject("tags",tags);

		return result;
	}
	
	//Edition -----------------------------------------------------------------
	
	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {
		ModelAndView result;
		Tag tag;

		tag = tagService.findOne(tagId);
		
		result = this.createEditModelAndView(tag);

		return result;
	}
	
	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tag tag, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tag);
		else
			try {
				this.tagService.save(tag);
				result = new ModelAndView("redirect:/tag/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tag, "application.commit.error");
			}

		return result;
	}
	
	@RequestMapping(value = "admin/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tag tag, final BindingResult binding) {
		ModelAndView result;
	
		try {
			this.tagService.delete(tag);
			result = new ModelAndView("redirect:/tag/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tag, "application.commit.error");
		}
	
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Tag tag) {
		ModelAndView result;

		result = this.createEditModelAndView(tag, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tag tag, final String message) {
		ModelAndView result;
		
		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);
		result.addObject("message", message);

		return result;
	}

}
