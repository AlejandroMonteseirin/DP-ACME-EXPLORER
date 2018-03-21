
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Auditor;
import domain.Note;

@Service
@Transactional
public class NoteService {

	// Managed repository
	@Autowired
	private NoteRepository			noteRepository;

	//Supporting services
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AuditorService			auditorService;
	@Autowired
	private AdministratorService	administratorService;


	// Constructor
	public NoteService() {
		super();
	}

	// Simple CRUD methods
	public Note create() {

		Note note;
		Date moment;
		Collection<Note> notes;
		Auditor auditor;

		auditor = (Auditor) this.actorService.findByPrincipal();
		note = new Note();
		moment = new Date(System.currentTimeMillis() - 1);
		notes = auditor.getNotes();
		notes.add(note);

		note.setAuditor(auditor);
		note.setCreationMoment(moment);

		return note;
	}

	public Note save(final Note note) {
		Assert.notNull(note);

		Note result;
		Date moment;
		Collection<Note> notes;

		moment = new Date(System.currentTimeMillis() - 1);

		note.setCreationMoment(moment);
		result = this.noteRepository.save(note);
		notes = note.getAuditor().getNotes();
		notes.add(result);
		note.getAuditor().setNotes(notes);
		this.auditorService.save(note.getAuditor());

		//Comprobamos si es spam
		this.administratorService.checkIsSpam(note.getRemark());

		return result;
	}

	public Note findOne(final int NoteId) {
		Note result;

		result = this.noteRepository.findOne(NoteId);

		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// OTHER BUSSINES METHODS -----------------------
	public void checkPrincipal(final Note note) {
		Auditor auditor;
		Auditor actor;

		auditor = note.getAuditor();
		actor = (Auditor) this.actorService.findByPrincipal();

		Assert.isTrue(auditor.equals(actor));
	}

	//B.13.1 Un Manager puede mostrar las notas que un auditor ha escrito en sus trips

	public Collection<Note> getNotesToManagerTrips(final int managerId) {
		return this.noteRepository.getNotesToManagerTrips(managerId);
	}

	// B.14.1 Un auditor puede listar y escribir sus notas en trips. Una vez que una nota
	// ha sido escrita, esta no puede ser borrada o modificada

	// B.16.4 Un Admin puede mostrar en el dashboard las estadisticas de notes por trip

	public Double[] getMinMaxAvgStddevNotesPerTrip() {
		return this.noteRepository.getMinMaxAvgStddevNotesPerTrip();
	}

	public Collection<Note> getNotesByAuditorId(final int auditorId) {
		return this.noteRepository.getNotesByAuditorId(auditorId);

	}

}
