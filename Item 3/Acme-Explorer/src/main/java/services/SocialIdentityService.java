package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SocialIdentityRepository socialIdentityRepository;

	@Autowired
	private ActorService actorService;
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public SocialIdentityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SocialIdentity create() {
		SocialIdentity result;
		result = new SocialIdentity();

		result.setNick("");
		result.setSocialNetworkName("");
		result.setPhotoUrl("");
		result.setProfileLink("");

		result.setActor(actorService.findByPrincipal());
		return result;
	}

	public SocialIdentity save(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		socialIdentity.setActor(actorService.findByPrincipal());

		SocialIdentity result;

		result = socialIdentityRepository.save(socialIdentity);

		// Comprobamos si es spam
		administratorService.checkIsSpam(socialIdentity.getNick());
		administratorService.checkIsSpam(socialIdentity.getPhotoUrl());
		administratorService.checkIsSpam(socialIdentity.getProfileLink());
		administratorService.checkIsSpam(socialIdentity.getSocialNetworkName());

		return result;
	}

	public void delete(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		Assert.isTrue(socialIdentity.getId() != 0);
		Assert.isTrue(socialIdentityRepository.exists(socialIdentity.getId()));

		socialIdentityRepository.delete(socialIdentity);
	}

	public void delete(Iterable<SocialIdentity> socialIdentities) {
		Assert.notNull(socialIdentities);
		socialIdentityRepository.delete(socialIdentities);
	}

	public SocialIdentity findOne(int socialIdentityId) {
		Assert.notNull(socialIdentityId);
		Assert.isTrue(socialIdentityId != 0);

		SocialIdentity result;

		result = socialIdentityRepository.findOne(socialIdentityId);

		return result;
	}
	
	public SocialIdentity findOneToEdit(int socialIdentityId) {
		Assert.notNull(socialIdentityId);
		Assert.isTrue(socialIdentityId != 0);

		SocialIdentity result;

		result = socialIdentityRepository.findOne(socialIdentityId);
		
		checkPrincipal(result);

		return result;
	}

	public Collection<SocialIdentity> findAll() {
		Collection<SocialIdentity> result;

		result = socialIdentityRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void addToPrincipal(SocialIdentity socialIdentity) {
		Actor actor = actorService.findByPrincipal();
		actor.getSocialIdentities().add(socialIdentity);

	}
	
	//Other business methods

//	public String checkPrincipal(Actor prin) {
//		String res = "";
//
//		for (Authority au : prin.getUserAccount().getAuthorities()) {
//			if (au.getAuthority().equals("ADMIN")) {
//				res = "redirect:/actor/admin/admin/edit.do";
//			} else if (au.getAuthority().equals("EXPLORER")) {
//				res = "redirect:/actor/admin/explorer/edit.do";
//			} else if (au.getAuthority().equals("RANGER")) {
//				res = "redirect:/actor/admin/ranger/edit.do";
//			} else if (au.getAuthority().equals("SPONSOR")) {
//				res = "redirect:/actor/admin/sponsor/edit.do";
//			} else if (au.getAuthority().equals("MANAGER")) {
//				res = "redirect:/actor/admin/manager/edit.do";
//			} else if (au.getAuthority().equals("AUDITOR")) {
//				res = "redirect:/actor/admin/auditor/edit.do";
//			}
//		}
//
//		return res;
//	}
	
	public void checkPrincipal(SocialIdentity sc){
		Actor a;
		
		a = actorService.findByPrincipal();
		
		Assert.isTrue(a.getSocialIdentities().contains(sc));
	}

}
