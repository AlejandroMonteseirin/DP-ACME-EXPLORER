package controllers;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ContactService;
import domain.Contact;
import domain.Explorer;
import domain.Manager;

@Controller
@RequestMapping("contact/explorer/")
public class ContactController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ContactService contactService;

	// Constructors -----------------------------------------------------------

	public ContactController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Contact contact;
	
		contact = contactService.create();
		result = createEditModelAndView(contact);
		
		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Collection<Contact> contacts;

		contacts = contactService.findAll();
		result = new ModelAndView("contact/list");
		result.addObject("contacts", contacts);

		return result;
	}
	
	@RequestMapping(value = "list-my", method = RequestMethod.GET)
	public ModelAndView display2() {
		ModelAndView result;
		Collection<Contact> contacts;

		Explorer e = (Explorer) actorService.findByPrincipal();
		contacts=e.getContacts();

		result = new ModelAndView("contact/list");
		result.addObject("contacts", contacts);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int contactId) {
		ModelAndView result;
		Contact contact;

		contact = contactService.findOneToEdit(contactId);

		result = this.createEditModelAndView(contact);

		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Contact contact,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(contact);
		else
			try {
				this.contactService.save(contact);
				result = new ModelAndView("redirect:list-my.do");
			} catch (final Throwable oops) {
				String errorMessage = "contact.commit.error"; //Debe estar en messages.propierties
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(contact, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Contact contact,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.contactService.delete(contact);
			result = new ModelAndView("redirect:list-my.do");
		} catch (final Throwable oops) {
			String errorMessage = "contact.commit.error"; 
			
			if(oops.getMessage().contains("message.error")){
				errorMessage = oops.getMessage();
			}
			result = this.createEditModelAndView(contact, errorMessage);
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Contact contact) {
		ModelAndView result;

		result = this.createEditModelAndView(contact, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Contact contact,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("contact/edit");
		result.addObject("contact", contact);
		result.addObject("message", message);

		return result;
	}
}