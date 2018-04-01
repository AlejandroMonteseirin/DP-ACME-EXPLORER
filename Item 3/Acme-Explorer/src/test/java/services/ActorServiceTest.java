package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserAccountService userAccountService;
	
	
	@Test
	public void testgetSuspiciousActors() {
		Collection<Actor> suspiciusActors =actorService.getSuspiciousActors();
		Assert.notEmpty(suspiciusActors);
	}
	@Test
	public void testFindByPrincipal() {
		authenticate("auditor1");
		Actor principal =actorService.findByPrincipal();
		Assert.notNull(principal);
	}
	@Test
	public void testFindByUserAccount() {
		List<UserAccount> userAccounts= (List<UserAccount>) userAccountService.findAll();
		UserAccount userAccount= userAccounts.get(0);
		Actor user =actorService.findByUserAccount(userAccount);
		Assert.notNull(user);
	}
	@Test
	public void testFindByUserAccountId() {
		List<UserAccount> userAccounts= (List<UserAccount>) userAccountService.findAll();
		UserAccount userAccount= userAccounts.get(0);
		int id=userAccount.getId();
		Actor user =actorService.findByUserAccountId(id);
		Assert.notNull(user);
	}
	@Test
	public void testGetType() {
		List<UserAccount> userAccounts= (List<UserAccount>) userAccountService.findAll();
		UserAccount userAccount= userAccounts.get(0);
		
		String type =actorService.getType(userAccount);
		Assert.notNull(type);
	}
	@Test
	public void testBan() {
		Actor pepon= (Actor) actorService.findAll().toArray()[0];
		pepon.setSuspicious(true); //debe ser sospechoso para poder ser baneado
		actorService.banActor(pepon);
	}
	@Test
	public void testUnBan() {
		Actor pepon= (Actor) actorService.findAll().toArray()[0];
		pepon.getUserAccount().setEnabled(false);
		actorService.unbanActor(pepon);
	}
	@Test
	public void updatePhone(){
		
		Actor pepon= (Actor) actorService.findAll().toArray()[0];
		System.out.println(pepon.getPhoneNumber());
		pepon.setPhoneNumber("+100 (666) 10");
		System.out.println(pepon.getPhoneNumber());
		
		
		
	}

	@Test
	public void testCreateActor() {

		Actor a = managerService.create();
		Assert.notNull(a);

	}

	@Test
	public void testSaveActor() {
		UserAccount u = new UserAccount();
		Authority a = new Authority();
		a.setAuthority("ADMIN");
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		u.setAuthorities(authorities);
		Actor saved;
		Collection<Actor> actors;
		Manager m = managerService.create();
		m.setName("Name1");
		m.setSurname("Surname1");
		saved = actorService.save(m);
		actors = actorService.findAll();
		Assert.isTrue(actors.contains(saved));

	}

	@Test
	public void testFindOneActor(){
		Actor saved;
		Manager m = managerService.create();
		m.setName("Name1");
		m.setSurname("Surname1");
		saved = actorService.save(m);
		int actorId = saved.getId();
		Actor a = actorService.findOne(actorId);
		Assert.isTrue(a.equals(saved));
	}
}
