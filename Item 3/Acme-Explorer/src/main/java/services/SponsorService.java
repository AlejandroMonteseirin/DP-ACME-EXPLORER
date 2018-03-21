package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.SocialIdentity;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository
	@Autowired
	private SponsorRepository sponsorRepository;

	// Supporting services
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ConfigurationService configurationService;

	// Constructor
	public SponsorService() {
		super();
	}

	// Simple CRUD methods
	public Sponsor create() {
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		Sponsor s;
		UserAccount ua;
		Authority auth;

		s = new Sponsor();
		ua = userAccountService.create();
		auth = new Authority();

		auth.setAuthority("SPONSOR");
		ua.addAuthority(auth);
		s.setUserAccount(ua);
		s.setSocialIdentities(socialIdentities);
		s.setSuspicious(false);

		return s;
	}

	public Collection<Sponsor> findAll() {
		return sponsorRepository.findAll();
	}

	public Sponsor findOne(int sponsorId) {
		Sponsor result;
		result = sponsorRepository.findOne(sponsorId);
		return result;
	}

	public Sponsor save(Sponsor sponsor) {
		Assert.notNull(sponsor);
		if (sponsor.getId() == 0) {
			Assert.isTrue(userAccountService.findByUsername(sponsor
					.getUserAccount().getUsername()) == null,
					"message.error.duplicatedUser");
		}
		Boolean create = false;

		// Comprobamos si se está creando el user
		if (sponsor.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(
					encoder.encodePassword(sponsor.getUserAccount()
							.getPassword(), null));
		}

		String tlf = configurationService.checkPhoneNumber(sponsor
				.getPhoneNumber());
		sponsor.setPhoneNumber(tlf);

		if (sponsor.getId() == 0) {
			sponsor.getUserAccount().setEnabled(true);
		}

		sponsor = sponsorRepository.save(sponsor);
		if (create) {
			folderService.createSystemFolders(sponsor);
		}

		return sponsor;
	}

	public void delete(Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);
		// Collection<Folder> folders = sponsor.getFolders();
		// Collection<Message> receivedMessages = sponsor.getReceivedMessages();
		// Collection<Message> sentMessages = sponsor.getSentMessages();
		// Collection<SocialIdentity> socialIdentities = sponsor
		// .getSocialIdentities();
		sponsorRepository.delete(sponsor);
		// folderService.delete(folders);
		// messageService.delete(receivedMessages);
		// messageService.delete(sentMessages);
		// socialIdentityService.delete(socialIdentities);

	}

	// Other business methods --------------------

	public Sponsor findByPrincipal() {
		Sponsor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Sponsor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor res;
		res = sponsorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Sponsor findByUserAccountId(int userAccountId) {
		return sponsorRepository.findByUserAccountId(userAccountId);
	}

}
