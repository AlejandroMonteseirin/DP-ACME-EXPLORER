package services;

	import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReplyRepository;
import domain.Manager;
import domain.Note;
import domain.Reply;

	@Service
	@Transactional
	public class ReplyService {
		
		// Managed repository
		@Autowired
		private ReplyRepository replyRepository;
		
		// Supporting services
		@Autowired
		private ActorService actorService;
		@Autowired
		private NoteService noteService;
		@Autowired
		private ManagerService managerService;
		@Autowired
		private AdministratorService administratorService;

		// Constructor
		public ReplyService() {
			super();
		}

		// Simple CRUD methods
		public Reply create(Note note) {
			Assert.notNull(note);
			
			Reply r;
			Date moment;
			Manager m;
			
			m = (Manager) actorService.findByPrincipal();
			r = new Reply();
			moment = new Date(System.currentTimeMillis()-1);
			
			r.setNote(note);
			r.setMoment(moment);
			r.setManager(m);
			
			return r;
		}
		
		public Reply save(Reply reply) {
			Assert.notNull(reply);
			
			Reply r;
			
			r = replyRepository.save(reply);
			r.getNote().setReply(r);
			Collection<Reply> managerReplies = r.getManager().getReplies();
			managerReplies.add(r);
			r.getManager().setReplies(managerReplies);
			
			managerService.save(r.getManager());
			noteService.save(r.getNote());
			
			//Comprobamos si es spam
			administratorService.checkIsSpam(reply.getText());
			
			return r;
		}

		public void delete(Reply reply) {
			Assert.notNull(reply);

			replyRepository.delete(reply);
		}
		
		public Collection<Reply> findAll() {
			Collection<Reply> replies;
			
			replies = replyRepository.findAll();
			
			return replies;
		}

		public Reply findOne(int replyId) {
			Assert.notNull(replyId);
			
			Reply r;
			
			r = replyRepository.findOne(replyId);
			
			return r;
		}


		// OTHER BUSSINES METHODS ---------------------------------
		
		public Reply getNoteReply(int noteId){
			return replyRepository.getNoteReply(noteId);
		}
	}

