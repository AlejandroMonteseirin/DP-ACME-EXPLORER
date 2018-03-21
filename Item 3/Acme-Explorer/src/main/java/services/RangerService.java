package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// MANAGED REPOSITORY ---------------

	@Autowired
	private RangerRepository rangerRepository;

	// Supporting services
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ConfigurationService configurationService;

	// CONSTRUCTOR ---------------

	public RangerService() {
		super();
	}

	// SIMPLE CRUD METHODS -----------
	public Ranger create() {
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();

		Ranger r;
		UserAccount ua;
		Authority auth;
		Collection<Trip> trips = new ArrayList<>();

		r = new Ranger();
		ua = this.userAccountService.create();
		auth = new Authority();
		ua.setEnabled(true);
		auth.setAuthority("RANGER");
		ua.addAuthority(auth);
		r.setUserAccount(ua);
		ua.isEnabled();
		r.setSocialIdentities(socialIdentities);
		r.setSuspicious(false);
		r.setTrips(trips);

		return r;
	}

	public Ranger save(Ranger ranger) {
		Assert.notNull(ranger);

		if (ranger.getId() == 0) {
			Assert.isTrue(userAccountService.findByUsername(ranger
					.getUserAccount().getUsername()) == null,
					"message.error.duplicatedUser");
		}

		Boolean create = false;

		// Comprobamos si se está creando el user
		if (ranger.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			ranger.getUserAccount().setPassword(
					encoder.encodePassword(ranger.getUserAccount()
							.getPassword(), null));
		}

		String tlf = configurationService.checkPhoneNumber(ranger
				.getPhoneNumber());
		ranger.setPhoneNumber(tlf);

		if (ranger.getId() == 0) {
			ranger.getUserAccount().setEnabled(true);
		}

		ranger = rangerRepository.save(ranger);
		if (create) {
			folderService.createSystemFolders(ranger);
		}

		return ranger;
	}

	public void delete(Ranger ranger) {
		Assert.notNull(ranger);
		// Collection<Folder> folders = ranger.getFolders();
		// Collection<Message> receivedMessages = ranger.getReceivedMessages();
		// Collection<Message> sentMessages = ranger.getSentMessages();
		// Collection<SocialIdentity> socialIdentities = ranger
		// .getSocialIdentities();
		this.rangerRepository.delete(ranger);
		// folderService.delete(folders);
		// messageService.delete(receivedMessages);
		// messageService.delete(sentMessages);
		// socialIdentityService.delete(socialIdentities);
	}

	public Collection<Ranger> findAll() {

		Collection<Ranger> rangers;

		rangers = this.rangerRepository.findAll();

		return rangers;
	}

	public Ranger findOne(final int rangerId) {
		Assert.notNull(rangerId);

		Ranger r;

		r = this.rangerRepository.findOne(rangerId);

		return r;
	}

	// Other business methods
	public Ranger findByTripId(final int tripId) {
		return this.rangerRepository.findByTripId(tripId);
	}

	public Double getRegisteredCurriculaRatio() {
		return this.rangerRepository.getRegisteredCurriculaRatio();
	}

	public Double getEndorsedCurriculaRatio() {
		return this.rangerRepository.getEndorsedCurriculaRatio();
	}

	public Double getSuspiciousRangersRatio() {
		return this.rangerRepository.getSuspiciousRangersRatio();
	}

	public Ranger getRangerByCurriculumId(final int curriculumId) {
		Assert.notNull(curriculumId);

		return this.rangerRepository.getRangerByCurriculumId(curriculumId);
	}

	public Collection<Ranger> getSuspiciousRangers() {
		return this.rangerRepository.getSuspiciousRangers();
	}

	public Ranger findByPrincipal() {
		Ranger res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Ranger findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Ranger res;
		res = rangerRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Ranger findByUserAccountId(int userAccountId) {
		return rangerRepository.findByUserAccountId(userAccountId);
	}
}
