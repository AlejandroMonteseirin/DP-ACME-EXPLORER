package security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services ----------------------------------------------------

	// Constructor -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public UserAccount create(){
		UserAccount ua;
		
		ua = new UserAccount();
		ua.setEnabled(true);
		
		
		return ua;
	}

	public void save(UserAccount userAccount){
		Assert.notNull(userAccount);
		
		userAccountRepository.save(userAccount);
	}

	public void delete(UserAccount userAccount){
		userAccountRepository.delete(userAccount);
	}
	
	public Collection<UserAccount> findAll(){
		Collection<UserAccount> result;

		result = userAccountRepository.findAll();

		return result;
	}

	public UserAccount findOne(int userAccountId){
		UserAccount result;

		result = userAccountRepository.findOne(userAccountId);

		return result;
	}
	
	// Other business methods -------------------------------------------------
	public UserAccount findByActor(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountRepository.findByActorId(actor.getId());

		return result;
	}
	
	public UserAccount findByUsername(String username){
		return userAccountRepository.findByUsername(username);
	}
	

}
