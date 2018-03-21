package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Auditor;
import domain.Audit;
import domain.Note;
import domain.SocialIdentity;

@Service
@Transactional
public class AuditorService {

	// Managed repository
	@Autowired
	private AuditorRepository auditorRepository;

	// Supporting services
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private FolderService folderService;

	// Constructor
	public AuditorService() {
		super();
	}

	// Simple CRUD methods
	public Auditor create() {

		Auditor a;
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		UserAccount ua;
		Authority auth;

		a = new Auditor();
		ua = userAccountService.create();
		auth = new Authority();

		auth.setAuthority("AUDITOR");
		ua.addAuthority(auth);
		a.setAudits(new ArrayList<Audit>());
		a.setNotes(new ArrayList<Note>());
		a.setUserAccount(ua);
		a.setSocialIdentities(socialIdentities);
		a.setSuspicious(false);

		return a;
	}

	public Auditor save(Auditor auditor) {
		Assert.notNull(auditor);
		if (auditor.getId() == 0) {
			Assert.isTrue(userAccountService.findByUsername(auditor
					.getUserAccount().getUsername()) == null,
					"message.error.duplicatedUser");
		}
		Boolean create = false;

		// Comprobamos si se está creando el user
		if (auditor.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			auditor.getUserAccount().setPassword(
					encoder.encodePassword(auditor.getUserAccount()
							.getPassword(), null));
		}

		String tlf = configurationService.checkPhoneNumber(auditor
				.getPhoneNumber());
		auditor.setPhoneNumber(tlf);

		if (auditor.getId() == 0) {
			auditor.getUserAccount().setEnabled(true);
		}

		auditor = auditorRepository.save(auditor);
		if (create) {
			folderService.createSystemFolders(auditor);
		}

		return auditor;
	}

	public void delete(Auditor auditor) {
		Assert.notNull(auditor);
		auditorRepository.delete(auditor);
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> auditors = auditorRepository.findAll();

		Assert.notNull(auditors);

		return auditors;
	}

	public Auditor findOne(int auditorId) {
		Assert.notNull(auditorId);

		Auditor a;

		a = auditorRepository.findOne(auditorId);

		return a;
	}

	// Other business methods

	public Auditor findByPrincipal() {
		Auditor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Auditor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Auditor res;
		res = auditorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Auditor findByUserAccountId(int userAccountId) {
		return auditorRepository.findByUserAccountId(userAccountId);
	}

}