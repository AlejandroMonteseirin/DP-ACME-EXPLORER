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

import services.ActorService;
import services.AuditService;
import services.TripService;
import domain.Audit;
import domain.Auditor;
import domain.LegalText;
import domain.Reply;
import domain.Trip;

@Controller
@RequestMapping("/audit/auditor")
public class AuditController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private AuditService auditService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private TripService tripService;
	
	// Constructors -----------------------------------------------------------

	public AuditController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Audit audit;

		audit = this.auditService.create();
		result = this.createEditModelAndView(audit);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audits;
		Auditor a;
		
		a = (Auditor) actorService.findByPrincipal();
		audits = auditService.getAuditsByAuditorId(a.getId());

		result = new ModelAndView("audit/list");
		result.addObject("requestURI", "audit/list.do");
		result.addObject("audits", audits);

		return result;
	}
	
	//Edition -----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;

		audit = auditService.findOneToEdit(auditId);

		result = this.createEditModelAndView(audit);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("redirect:list.do");

		if (binding.hasErrors())
			result = this.createEditModelAndView(audit);
		else
			try {
				this.auditService.save(audit);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "application.commit.error");
			}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView result;
	
		try {
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit, "application.commit.error");
		}
	
		return result;
	}

	//Display ----------------------------------------------------------------
	@RequestMapping(value="/display",method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int auditId){
		
		ModelAndView result;
		Audit r;
		
		r = auditService.findOne(auditId);
		
		result = new ModelAndView("audit/display");
		result.addObject("audit",r);
		
		return result;
		
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String message) {
		ModelAndView result;
		Collection<Trip> trips;
		
		trips = tripService.getVisibleTrips();
		
		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("trips", trips);
		result.addObject("message", message);

		return result;
	}

}
