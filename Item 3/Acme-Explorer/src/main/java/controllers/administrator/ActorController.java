package controllers.administrator;

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

import services.ActorService;
import services.AdministratorService;
import services.AuditorService;
import services.ExplorerService;
import services.ManagerService;
import services.RangerService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private ExplorerService explorerService;

	@Autowired
	private RangerService rangerService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private AuditorService auditorService;

	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "admin/list", method = RequestMethod.GET)
	public ModelAndView listSuspicious() {
		ModelAndView result;
		Collection<Actor> suspiciousActors;

		suspiciousActors = actorService.getSuspiciousActors();

		result = new ModelAndView("actor/list");
		result.addObject("actors", suspiciousActors);

		return result;
	}

	// Edition
	// Admin----------------------------------------------------------------

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public ModelAndView editAdmin() {
		ModelAndView result;
		Administrator administrator;
		administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		result = createEditModelAndViewAdmin(administrator);
		result.addObject(administrator);
		return result;

	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdmin(@Valid Administrator actor,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewAdmin(actor);
		} else {
			try {

				this.administratorService.save(actor);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewAdmin(actor,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	// Admin------------------------------------------------------

	protected ModelAndView createEditModelAndViewAdmin(Administrator actor) {
		ModelAndView result;

		result = createEditModelAndViewAdmin(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAdmin(Administrator admin,
			String message) {
		ModelAndView result;

		Collection<SocialIdentity> socialIdentities;
		socialIdentities = admin.getSocialIdentities();

		result = new ModelAndView("administrator/edit");
		result.addObject("admin", admin);
		result.addObject("socialIdentities", socialIdentities);

		return result;

	}

	// Edition
	// Explorer----------------------------------------------------------------

	@RequestMapping(value = "/explorer/edit", method = RequestMethod.GET)
	public ModelAndView editExplorer() {
		ModelAndView result;
		Explorer explorer;
		explorer = explorerService.findByPrincipal();
		Assert.notNull(explorer);
		result = createEditModelAndViewExplorer(explorer);
		result.addObject(explorer);
		return result;

	}

	@RequestMapping(value = "/explorer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveExplorer(@Valid Explorer explorer,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewExplorer(explorer);
		} else {
			try {

				this.explorerService.save(explorer);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewExplorer(explorer,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	// Explorer------------------------------------------------------

	protected ModelAndView createEditModelAndViewExplorer(Explorer explorer) {
		ModelAndView result;

		result = createEditModelAndViewExplorer(explorer, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewExplorer(Explorer explorer,
			String message) {
		ModelAndView result;

		Collection<SocialIdentity> socialIdentities;
		socialIdentities = explorer.getSocialIdentities();

		result = new ModelAndView("explorer/edit");
		result.addObject("explorer", explorer);
		result.addObject("socialIdentities", socialIdentities);

		return result;

	}

	// Edition
	// Manager----------------------------------------------------------------

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView editManager() {
		ModelAndView result;
		Manager manager;
		manager = managerService.findByPrincipal();
		Assert.notNull(manager);
		result = createEditModelAndViewManager(manager);
		result.addObject(manager);
		return result;

	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(@Valid Manager manager,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewManager(manager);
		} else {
			try {

				this.managerService.save(manager);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewManager(manager,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	// Manager------------------------------------------------------

	protected ModelAndView createEditModelAndViewManager(Manager manager) {
		ModelAndView result;

		result = createEditModelAndViewManager(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewManager(Manager manager,
			String message) {
		ModelAndView result;

		Collection<SocialIdentity> socialIdentities;
		socialIdentities = manager.getSocialIdentities();

		result = new ModelAndView("manager/edit");
		result.addObject("manager", manager);
		result.addObject("socialIdentities", socialIdentities);

		return result;

	}

	// Edition
	// Ranger----------------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.GET)
	public ModelAndView editRanger() {
		ModelAndView result;
		Ranger ranger;
		ranger = rangerService.findByPrincipal();
		Assert.notNull(ranger);
		result = createEditModelAndViewRanger(ranger);
		result.addObject(ranger);
		return result;

	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRanger(@Valid Ranger ranger, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewRanger(ranger);
		} else {
			try {

				this.rangerService.save(ranger);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewRanger(ranger,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	// Ranger------------------------------------------------------

	protected ModelAndView createEditModelAndViewRanger(Ranger ranger) {
		ModelAndView result;

		result = createEditModelAndViewRanger(ranger, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewRanger(Ranger ranger,
			String message) {
		ModelAndView result;

		Collection<SocialIdentity> socialIdentities;
		socialIdentities = ranger.getSocialIdentities();

		result = new ModelAndView("ranger/edit");
		result.addObject("ranger", ranger);
		result.addObject("socialIdentities", socialIdentities);

		return result;

	}

	// Edition
	// Sponsor----------------------------------------------------------------

	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Sponsor sponsor;
		sponsor = sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		result = createEditModelAndView(sponsor);
		result.addObject(sponsor);
		return result;

	}

	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsor);
		} else {
			try {

				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(sponsor,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	// Sponsor------------------------------------------------------

	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView result;

		result = createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor,
			String message) {
		ModelAndView result;

		Collection<SocialIdentity> socialIdentities;
		socialIdentities = sponsor.getSocialIdentities();

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("socialIdentities", socialIdentities);

		return result;

	}

	// Edition
	// Sponsor----------------------------------------------------------------

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.GET)
	public ModelAndView editAuditor() {
		ModelAndView result;
		Auditor auditor;
		auditor = auditorService.findByPrincipal();
		Assert.notNull(auditor);
		result = createEditModelAndViewAuditor(auditor);
		result.addObject(auditor);
		return result;

	}

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuditor(@Valid Auditor auditor,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewAuditor(auditor);
		} else {
			try {

				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewAuditor(auditor,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	// Auditor------------------------------------------------------

	protected ModelAndView createEditModelAndViewAuditor(Auditor auditor) {
		ModelAndView result;

		result = createEditModelAndViewAuditor(auditor, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAuditor(Auditor auditor,
			String message) {
		ModelAndView result;

		Collection<SocialIdentity> socialIdentities;
		socialIdentities = auditor.getSocialIdentities();

		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", auditor);
		result.addObject("socialIdentities", socialIdentities);

		return result;

	}

	// Ban/Unban --------------------------------------------------------------

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam(required = true) int actorId) {
		ModelAndView result;
		Collection<Actor> suspiciousActors;
		Actor a;

		a = actorService.findOne(actorId);

		actorService.banActor(a);
		suspiciousActors = actorService.getSuspiciousActors();

		result = new ModelAndView("actor/list");
		result.addObject("actors", suspiciousActors);

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam(required = true) int actorId) {
		ModelAndView result;
		Collection<Actor> suspiciousActors;
		Actor a;

		a = actorService.findOne(actorId);

		actorService.unbanActor(a);
		suspiciousActors = actorService.getSuspiciousActors();

		result = new ModelAndView("actor/list");
		result.addObject("actors", suspiciousActors);

		return result;
	}

}
