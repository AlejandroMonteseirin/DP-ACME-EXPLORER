
package controllers.sponsor;

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
import services.CreditCardService;
import services.SponsorService;
import services.SponsorshipService;
import services.TripService;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipController {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private SponsorService		sponsorService;
	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private TripService tripService;

	// Constructor
	// -------------------------------------------------------------------

	public SponsorshipController() {
		super();
	}

	// List
	// -------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Sponsorship> sponsorships;
		Sponsor sponsor;
		sponsor = this.sponsorService.findByPrincipal();
		sponsorships = sponsor.getSponsorships();

		res = new ModelAndView("sponsorship/list");
		res.addObject("requestURI", "sponsorship/list.do");
		res.addObject("sponsorships", sponsorships);

		return res;
	}

	// Creation --------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		res = this.createEditModelAndView(sponsorship);

		return res;
	}

	// Edit-------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOneToEdit(sponsorshipId);

		result = this.createEditModelAndView(sponsorship);
		result.addObject("sponsorship", sponsorship);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(sponsorship);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(sponsorship, "application.commit.error");

			}
		return res;

	}
	
	// Display -----------------------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship s;
		
		s = this.sponsorshipService.findOne(sponsorshipId);
		
		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship",s);
		
		return result;
	}

	// Ancillary methods
	// -------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView res;

		res = this.createEditModelAndView(sponsorship, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView res;
		Collection<Trip> trips;

		res = new ModelAndView("sponsorship/edit");
		trips = tripService.getVisibleTrips();
		
		res.addObject("sponsorship", sponsorship);
		res.addObject("message", message);
		res.addObject("trips", trips);

		return res;
	}

}
