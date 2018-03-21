package controllers.manager;

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
import services.NoteService;
import services.ReplyService;
import controllers.AbstractController;
import domain.Folder;
import domain.Message;
import domain.Note;
import domain.Reply;

@Controller
@RequestMapping("/reply")
public class ReplyController extends AbstractController{
	
	// Services ---------------------------------------------------------------

	@Autowired
	private NoteService noteService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private ReplyService replyService;
	
	// Constructors -----------------------------------------------------------

		public ReplyController() {
			super();
		}
		
	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required=true) final Integer noteId) {
		ModelAndView result;
		Note note;
		Reply reply;
		
		
		note = noteService.findOne(noteId);
		reply = this.replyService.create(note);
		result = this.createEditModelAndView(reply);
		result.addObject("note",note);
	
		return result;
	}
	
	//Edition -----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int replyId) {
		ModelAndView result;
		Reply reply;

		reply = this.replyService.findOne(replyId);
		result = this.createEditModelAndView(reply);

		return result;
	}
	
	@RequestMapping(value = "manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Reply reply, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(reply);
		else
			try {
				this.replyService.save(reply);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(reply, "note.commit.error");
			}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Reply reply, final BindingResult binding) {
		ModelAndView result;

		try {
			this.replyService.delete(reply);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(reply, "card.commit.error");
		}

		return result;
	}
	
	//Display ----------------------------------------------------------------
	@RequestMapping(value="/display",method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int replyId){
		
		ModelAndView result;
		Reply r;
		
		r = replyService.findOne(replyId);
		
		result = new ModelAndView("reply/display");
		result.addObject("reply",r);
		
		return result;
		
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(final Reply reply) {
		ModelAndView result;

		result = this.createEditModelAndView(reply, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Reply reply, final String message) {
		ModelAndView result;
		
		result = new ModelAndView("reply/edit");
		result.addObject("reply", reply);
		result.addObject("message", message);

		return result;
	}

}
