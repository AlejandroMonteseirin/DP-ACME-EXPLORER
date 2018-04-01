package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Folder;
import domain.Manager;
import domain.Note;
import domain.Reply;
import domain.SocialIdentity;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	// Managed repository
	@Autowired
	private ManagerRepository managerRepository;

	// Supporting services
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private SocialIdentityService socialIdentityService;

	// Constructor
	public ManagerService() {
		super();
	}

	// Simple CRUD methods
	public Manager create() {

		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		Manager m;
		UserAccount ua;
		Authority auth;

		m = new Manager();
		ua = userAccountService.create();
		auth = new Authority();

		auth.setAuthority("MANAGER");
		ua.addAuthority(auth);
		m.setUserAccount(ua);
		m.setTrips(new ArrayList<Trip>());
		m.setSurvivalClasses(new ArrayList<SurvivalClass>());
		m.setNotes(new ArrayList<Note>());
		m.setReplies(new ArrayList<Reply>());
		m.setSocialIdentities(socialIdentities);
		m.setSuspicious(false);
		m.setFolders(new ArrayList<Folder>());
		m.setFolders(new ArrayList<Folder>());

		return m;
	}

	public Manager save(Manager manager) {
		Assert.notNull(manager);

		if (manager.getId() == 0) {
			Assert.isTrue(userAccountService.findByUsername(manager
					.getUserAccount().getUsername()) == null,
					"message.error.duplicatedUser");
		}
		Boolean create = false;

		// Comprobamos si se está creando el user
		if (manager.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			manager.getUserAccount().setPassword(
					encoder.encodePassword(manager.getUserAccount()
							.getPassword(), null));
		}

		if (manager.getPhoneNumber() != null) {
			String tlf = configurationService.checkPhoneNumber(manager
					.getPhoneNumber());
			manager.setPhoneNumber(tlf);
		}

		if (manager.getId() == 0) {
			manager.getUserAccount().setEnabled(true);
		}

		manager = managerRepository.save(manager);
		if (create) {
			folderService.createSystemFolders(manager);

		}

		return manager;
	}

	public void delete(Manager manager) {
		Assert.notNull(manager);
		Collection<Folder> folders = manager.getFolders();
		Collection<SocialIdentity> socialIdentities = manager
				.getSocialIdentities();

		folderService.delete(folders);
		socialIdentityService.delete(socialIdentities);
		managerRepository.delete(manager);
	}

	public Collection<Manager> findAll() {

		Collection<Manager> managers;

		managers = managerRepository.findAll();

		return managers;
	}

	public Manager findOne(int managerId) {
		Assert.notNull(managerId);

		Manager m;

		m = managerRepository.findOne(managerId);

		return m;
	}

	public Manager findOneToEdit(int managerId) {
		Assert.notNull(managerId);
		Manager m;

		m = managerRepository.findOne(managerId);

		checkPrincipal(m);

		return m;
	}

	// OTHER BUSSINES METHODS ----------------------------

	public void checkPrincipal(Manager m) {
		Manager principal;

		principal = (Manager) actorService.findByPrincipal();

		Assert.isTrue(m.equals(principal));

	}

	// B.16.1 Un Admin puede listar Manager sospechosos
	// Un actor es sospechoso si publica algun dato con spam word

	public Collection<Manager> getSuspiciousManagers() {
		return managerRepository.getSuspiciousManagers();
	}

	// B.16.4 Ratio de Managers sospechosos para el dashboard

	public Double getSuspiciousManagersRatio() {
		return managerRepository.getSuspiciousManagersRatio();
	}

	public Manager getManagerFromApplicationId(int applicationId) {
		return managerRepository.getManagerFromApplicationId(applicationId);
	}

	public Manager findByPrincipal() {
		Manager res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Manager findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager res;
		res = managerRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Manager findByUserAccountId(int userAccountId) {
		return managerRepository.findByUserAccountId(userAccountId);
	}
}
