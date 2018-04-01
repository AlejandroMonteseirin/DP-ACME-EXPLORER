package services;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private UserAccountService userAccountService;
	
	@Test
	public void createSaveDelete(){
		Sponsor admin, aSaved;
		Collection<Sponsor> aBefore, aAfter;
		
		admin = sponsorService.create();
		Assert.notNull(admin);
		
		//Comprobamos save
		UserAccount ua = userAccountService.create();
		Collection<Authority> auth = new ArrayList<Authority>();
		Authority au = new Authority();
		au.setAuthority(Authority.MANAGER);
		auth.add(au);
		ua.setAuthorities(auth);
		ua.setEnabled(true);
		ua.setUsername("prueba");
		ua.setPassword("prueba");
		admin.setUserAccount(ua);
		
		admin.setName("name");
		admin.setSurname("surname");
		admin.setEmail("email@email.es");
		
		aSaved = sponsorService.save(admin);
		Assert.notNull(aSaved);
		
		aBefore = sponsorService.findAll();
		Assert.isTrue(aBefore.contains(aSaved));
		
		//Comprobamos delete
		sponsorService.delete(aSaved);
		
		aAfter = sponsorService.findAll();
		
		Assert.isTrue(!aAfter.contains(aSaved));
	}

//	@Test
//	public void createAndSaveSponsor() {
//
//		Sponsor sponsor = sponsorService.findOne(1010);
//		System.out.println(sponsor.getAddress());
//		Sponsor spons = sponsorService.create();
		// spons.setAddress("email");
		// spons.setEmail("email2");

		// System.out.println(spons.getFolders());

//		Collection<Sponsorship> collspon = new ArrayList<Sponsorship>();
//		spons.setSponsorships(collspon);
//		spons.setIsBanned(false);
//		spons.setName("sponsor 123");
//		spons.setPhoneNumber("651 52 56 83");
//		//spons.setReceivedMessages(messageService.findAll());
//		//spons.setSentMessages(messageService.findAll());
//		spons.setSurname("nabo");
//		spons.setSuspicious(false);
//		// sponsorService.save(spons);
//		sponsor.setAddress("nabo@gmail.com");
//		// sponsorService.save(sponsor);
//		//Assert.notNull(sponsor);

//		for (Folder f : spons.getFolders()) {
//			System.out.println(f.getName());
//		}
//		System.out.println(spons.getPhoneNumber());
//		System.out.println(sponsor.getAddress());
//
//		Collection<Sponsor> collafter = sponsorService.findAll();
//		sponsorService.delete(sponsor);

		//Assert.isTrue(!(collafter.contains(sponsor)));

	}

	
