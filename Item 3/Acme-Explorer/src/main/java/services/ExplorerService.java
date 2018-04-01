package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Explorer;
import domain.Finder;
import domain.SocialIdentity;

@Service
@Transactional
public class ExplorerService {

	// Managed repository
	@Autowired
	private ExplorerRepository explorerRepository;

	// Supporting services
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private FinderService finderService;

	// Constructor
	public ExplorerService() {
		super();
	}

	// Simple CRUD methods
	public Explorer create() {
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		Explorer e;
		UserAccount ua;
		Authority auth;

		e = new Explorer();
		ua = this.userAccountService.create();

		auth = new Authority();

		auth.setAuthority("EXPLORER");
		ua.addAuthority(auth);
		e.setUserAccount(ua);
		e.setSocialIdentities(socialIdentities);
		e.setSuspicious(false);

		return e;
	}

	public Explorer save(Explorer explorer) {
		Assert.notNull(explorer);

		if (explorer.getId() == 0) {
			Assert.isTrue(userAccountService.findByUsername(explorer
					.getUserAccount().getUsername()) == null,
					"message.error.duplicatedUser");
		}

		Boolean create = false;
		Finder f;

		// Comprobamos si se está creando el user
		if (explorer.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			explorer.getUserAccount().setPassword(
					encoder.encodePassword(explorer.getUserAccount()
							.getPassword(), null));
		}
		
		if (explorer.getPhoneNumber() != null) {
			String tlf = configurationService.checkPhoneNumber(explorer
					.getPhoneNumber());
			explorer.setPhoneNumber(tlf);
		}
		
		if (explorer.getId() == 0) {
			explorer.getUserAccount().setEnabled(true);
		}
		explorer = explorerRepository.save(explorer);
		if (create) {
			folderService.createSystemFolders(explorer);
			f = finderService.create();
			f.setExplorer(explorer);
			finderService.save(f);
		}

		return explorer;
	}

	public void delete(Explorer explorer) {
		Assert.notNull(explorer);

		explorerRepository.delete(explorer);

	}

	public Collection<Explorer> findAll() {
		return explorerRepository.findAll();

	}

	public Explorer findOne(int explorerId) {
		Assert.notNull(explorerId);

		Explorer e;

		e = explorerRepository.findOne(explorerId);

		return e;
	}

	// Other business methods

	public Explorer findByApplicationId(int applicatioId) {
		return explorerRepository.findByApplicationId(applicatioId);
	}

	public Explorer findByPrincipal() {
		Explorer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Explorer findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Explorer res;
		res = explorerRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Explorer findByUserAccountId(int userAccountId) {
		return explorerRepository.findByUserAccountId(userAccountId);
	}

}
