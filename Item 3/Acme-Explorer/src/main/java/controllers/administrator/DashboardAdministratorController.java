
package controllers.administrator;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.AuditService;
import services.LegalTextService;
import services.ManagerService;
import services.NoteService;
import services.RangerService;
import services.TripService;
import controllers.AbstractController;
import domain.LegalText;
import domain.Trip;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Service -----------------------------------------------------

	@Autowired
	private TripService			tripService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private LegalTextService	legalTextService;
	@Autowired
	private NoteService			noteService;
	@Autowired
	private AuditService		auditService;
	@Autowired
	private RangerService		rangerService;
	@Autowired
	private ManagerService		managerService;


	// Constructor -------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Dashboard ------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");

		final Double[] appl = this.applicationService.getAvgMinMaxStdevPerTrip();
		final Double avgApplications = appl[0];
		final Double minApplications = appl[1];
		final Double maxApplications = appl[2];
		final Double stdevApplications = appl[3];

		final Double[] trip1 = this.tripService.tripsPerManagerStats();
		final Double avgTrip1 = trip1[0];
		final Double minTrip1 = trip1[1];
		final Double maxTrip1 = trip1[2];
		final Double stdevTrip1 = trip1[3];

		final Double[] trip2 = this.tripService.tripsPriceStats();
		final Double avgTrip2 = trip2[0];
		final Double minTrip2 = trip2[1];
		final Double maxTrip2 = trip2[2];
		final Double stdevTrip2 = trip2[3];

		final Double[] trip3 = this.tripService.tripsPerRangerStats();
		final Double avgTrip3 = trip3[0];
		final Double minTrip3 = trip3[1];
		final Double maxTrip3 = trip3[2];
		final Double stdevTrip3 = trip3[3];

		final Double ratioApplicationsPending = this.applicationService.getPendingRatio();
		final Double ratioApplicationsAccepted = this.applicationService.getAcceptedRatio();
		final Double ratioApplicationsCancelled = this.applicationService.getCancelledRatio();
		final Double ratioApplicationsDue = this.applicationService.getDueRatio();

		final Double ratioTripsCancelledVsOrganised = this.tripService.ratioTripsCancelled();

		final Collection<Trip> tripsMoreApplications = this.tripService.tripsMoreAplications();

		final Map<LegalText, Integer> countLegalText = this.legalTextService.getLegalTextReferenceCount();

		final Double[] note = this.noteService.getMinMaxAvgStddevNotesPerTrip();
		final Double avgNote = note[0];
		final Double minNote = note[1];
		final Double maxNote = note[2];
		final Double stdevNote = note[3];

		final Double[] audit = this.auditService.getMinMaxAvgStddevAuditsPerTrip();
		final Double avgAudit = audit[0];
		final Double minAudit = audit[1];
		final Double maxAudit = audit[2];
		final Double stdevAudit = audit[3];

		final Double ratioTripsAudit = this.auditService.auditRecordRatio();

		final Double ratioManagerCurriculaReg = this.rangerService.getRegisteredCurriculaRatio();
		final Double ratioManagerCurriculaEnd = this.rangerService.getEndorsedCurriculaRatio();

		final Double ratioSuspiciousManager = this.managerService.getSuspiciousManagersRatio();
		final Double ratioSuspiciousRanger = this.rangerService.getSuspiciousRangersRatio();

		result.addObject("avgApplications", avgApplications);
		result.addObject("minApplications", minApplications);
		result.addObject("maxApplications", maxApplications);
		result.addObject("stdevApplications", stdevApplications);

		result.addObject("avgTrip1", avgTrip1);
		result.addObject("minTrip1", minTrip1);
		result.addObject("maxTrip1", maxTrip1);
		result.addObject("stdevTrip1", stdevTrip1);

		result.addObject("avgTrip2", avgTrip2);
		result.addObject("minTrip2", minTrip2);
		result.addObject("maxTrip2", maxTrip2);
		result.addObject("stdevTrip2", stdevTrip2);

		result.addObject("avgTrip3", avgTrip3);
		result.addObject("minTrip3", minTrip3);
		result.addObject("maxTrip3", maxTrip3);
		result.addObject("stdevTrip3", stdevTrip3);

		result.addObject("ratioApplicationsPending", ratioApplicationsPending);
		result.addObject("ratioApplicationsAccepted", ratioApplicationsAccepted);
		result.addObject("ratioApplicationsCancelled", ratioApplicationsCancelled);
		result.addObject("ratioApplicationsDue", ratioApplicationsDue);

		result.addObject("ratioTripsCancelledVsOrganised", ratioTripsCancelledVsOrganised);
		result.addObject("tripsMoreApplications", tripsMoreApplications);

		result.addObject("countLegalText", countLegalText);

		result.addObject("avgNote", avgNote);
		result.addObject("minNote", minNote);
		result.addObject("maxNote", maxNote);
		result.addObject("stdevNote", stdevNote);

		result.addObject("avgAudit", avgAudit);
		result.addObject("minAudit", minAudit);
		result.addObject("maxAudit", maxAudit);
		result.addObject("stdevAudit", stdevAudit);

		result.addObject("ratioTripsAudit", ratioTripsAudit);

		result.addObject("ratioManagerCurriculaReg", ratioManagerCurriculaReg);
		result.addObject("ratioManagerCurriculaEnd", ratioManagerCurriculaEnd);

		result.addObject("ratioSuspiciousManager", ratioSuspiciousManager);
		result.addObject("ratioSuspiciousRanger", ratioSuspiciousRanger);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;

	}
}
