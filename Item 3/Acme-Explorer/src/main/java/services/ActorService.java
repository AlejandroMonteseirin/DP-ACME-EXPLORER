package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private SocialIdentityService socialIdentityService;
	@Autowired
	private MessageService messageService;

	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Actor save(Actor actor) {
		Assert.notNull(actor);
		return actorRepository.save(actor);
	}

	public Actor findOne(int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = actorRepository.findOne(actorId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// OTHER BUSSINES METHODS -------------------------------------------------

	public Collection<Actor> getSuspiciousActors() {
		return actorRepository.getSuspiciousActors();
	}

	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Actor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor res;
		res = actorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Actor findByUserAccountId(int userAccountId) {
		return actorRepository.findByUserAccountId(userAccountId);
	}

	public String getType(UserAccount userAccount) {

		List<Authority> authorities = new ArrayList<Authority>(
				userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}

	// B.16.2 Un Admin puede banear un actor que sea sospechoso(desactivar
	// su cuenta de usuario)
	public void banActor(Actor actor) {
		Assert.isTrue(actor.getSuspicious());
		Assert.isTrue(actor.getUserAccount().isEnabled());

		actor.getUserAccount().setEnabled(false);
		this.save(actor);
	}

	// B.16.3 Un Admin puede quitar el baneo a un actor(reactivar su
	// cuenta)
	public void unbanActor(Actor actor) {
		Assert.isTrue(!actor.getUserAccount().isEnabled());

		actor.getUserAccount().setEnabled(true);
		this.save(actor);
	}

	// public String checkTelefono(String tlf) {
	//
	// String res = tlf;
	// String res1 = tlf;
	// String res2 = tlf;
	// String result = "";
	//
	// Pattern patron = Pattern.compile("\\d{4}");
	// Matcher encaja = patron.matcher(res);
	// System.out.println(encaja);
	// Pattern patron1 = Pattern
	// .compile("(\\+\\d{1,3}\\s)?(\\(\\d{1,3}\\)\\s)?\\d{4}");
	// Matcher encaja1 = patron1.matcher(res1);
	// System.out.println(encaja1);
	//
	// Pattern patron2 = Pattern.compile("(\\+\\d{1,3}\\s)?\\d{4}");
	// Matcher encaja2 = patron2.matcher(res2);
	// System.out.println(encaja2);
	//
	// if (encaja.matches()) {
	// String nuevo = "";
	// Random randon = new Random();
	// Integer i = randon.nextInt(998);
	// i++;
	// nuevo = "+" + i.toString() + " " + tlf;
	// result = nuevo;
	// } else if (encaja1.matches()) {
	// result = res1;
	//
	// } else if (encaja2.matches()) {
	// result = res2;
	//
	// }
	//
	// return result;
	//
	// }

//	public void checkPhoneNumber(String tlf) {
//		
//		if(!tlf.startsWith("+") && tlf.length()>4){
//			
//		}

//		String result = tlf;
//
//		Pattern patron = Pattern.compile("\\d{4,100}");
//		Matcher encaja = patron.matcher(result);
//
//		if (encaja.matches()) {
//			String nuevo = "";
//			Random randon = new Random();
//			Integer i = randon.nextInt(998);
//			i++;
//			nuevo = "+" + i.toString() + " " + tlf;
//			result = nuevo;
//
//		}

//	}

}