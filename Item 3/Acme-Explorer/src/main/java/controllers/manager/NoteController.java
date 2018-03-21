package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Manager;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NoteService noteService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private TripService tripService;

	// Constructors -----------------------------------------------------------

	public NoteController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView listManagerNotes() {
		ModelAndView result;
		Collection<Note> notes;
		Manager m;

		m = (Manager) actorService.findByPrincipal();
		notes = this.noteService.getNotesToManagerTrips(m.getId());

		result = new ModelAndView("note/list");
		result.addObject("requestURI", "note/manager/list.do");
		result.addObject("notes", notes);

		return result;
	}
	
	@RequestMapping(value = "/auditor/list", method = RequestMethod.GET)
	public ModelAndView listAuditorNotes() {
		ModelAndView result;
		Collection<Note> notes;
		Auditor a;

		a = (Auditor) actorService.findByPrincipal();
		notes = this.noteService.getNotesByAuditorId(a.getId());

		result = new ModelAndView("note/list");
		result.addObject("requestURI", "note/auditor/list.do");
		result.addObject("notes", notes);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/auditor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		result = this.createEditModelAndView(note);

		return result;
	}

	//Edition -----------------------------------------------------------------
	
	@RequestMapping(value = "auditor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String message) {
		ModelAndView result;
		Collection<Trip> endedTrips;
		
		endedTrips = tripService.getEndedTrips();
		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("trips", endedTrips);
		result.addObject("message", message);

		return result;
	}

}
