package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Auditor;
import domain.Explorer;
import domain.Manager;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Sponsor;

@Service
@Transactional
public class AdministratorService {

	// Managed repository
	@Autowired
	private AdministratorRepository administratorRepository;

	// Supporting services
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private ActorService actorService;

	@Autowired
	private FolderService folderService;
	@Autowired
	private UserAccountService userAccountService;

	// Constructor
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods
	public Administrator create() {

		Administrator a;
		Collection<SocialIdentity> socialIdentities;
		UserAccount ua;
		Authority auth;

		a = new Administrator();
		socialIdentities = new ArrayList<SocialIdentity>();
		ua = userAccountService.create();
		auth = new Authority();

		auth.setAuthority("ADMIN");
		ua.addAuthority(auth);

		a.setUserAccount(ua);
		a.setSocialIdentities(socialIdentities);
		a.setSuspicious(false);

		return a;

	}

	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);

		Administrator result;

		result = administratorRepository.save(administrator);
		folderService.createSystemFolders(result);

		return result;
	}

	public void delete(Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		// Collection<Folder> folders = administrator.getFolders();
		// Collection<Message> receivedMessages =
		// administrator.getReceivedMessages();
		// Collection<Message> sentMessages = administrator.getSentMessages();
		// Collection<SocialIdentity> socialIdentities = administrator
		// .getSocialIdentities();
		administratorRepository.delete(administrator);
		// folderService.delete(folders);
		// messageService.delete(receivedMessages);
		// messageService.delete(sentMessages);
		// socialIdentityService.delete(socialIdentities);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(int administratorId) {
		Administrator result;

		result = administratorRepository.findOne(administratorId);

		return result;
	}

	// Other business methods
	public boolean checkIsSpam(String text) {
		Collection<String> spamWords;
		Boolean isSpam = false;
		Actor a = actorService.findByPrincipal();
		String type = actorService.getType(a.getUserAccount());
		
		if (type.equals("EXPLORER")) {
			a = (Explorer) a;
		} else if (type.equals("AUDITOR")) {
			a = (Auditor) a;
		} else if (type.equals("RANGER")) {
			a = (Ranger) a;
		} else if (type.equals("MANAGER")) {
			a = (Manager) a;
		} else if (type.equals("SPONSOR")) {
			a = (Sponsor) a;
		}

		if (text == null) {
			return isSpam;
		} else {
			text = text.toLowerCase();
			spamWords = configurationService.getSpamWords();
			for (String spamword : spamWords) {
				if (text.contains(spamword)) {
					isSpam = true;
					a.setSuspicious(true);
				}
			}
		}
		return isSpam;
	}

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Administrator findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator res;
		res = administratorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Administrator findByUserAccountId(int userAccountId) {
		return administratorRepository.findByUserAccountId(userAccountId);
	}

}