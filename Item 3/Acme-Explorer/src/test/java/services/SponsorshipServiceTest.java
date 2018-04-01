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

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private SponsorshipService sponsorshipService;
	@Autowired
	private TripService tripService;
	@Autowired
	private CreditCardService creditcardService;

	@Test
	public void createAndSaveSponsorship() {
		authenticate("sponsor1");
		
		Sponsorship sp = sponsorshipService.create();
		sp.setBannerURL("http://marca.com");
		sp.setInfoPageLink("pagina 1");

		sp.setTrip(tripService.findOne(2534));

		Collection<CreditCard> cred = creditcardService.findAll();
		List<CreditCard> spp = new ArrayList<CreditCard>(cred);
		CreditCard sps = spp.get(0);
		sp.setCreditCard(creditcardService.findOne(sps.getId()));
		List<Sponsor> sponsors = new ArrayList<Sponsor>(
				sponsorService.findAll());
		sp.setSponsor(sponsors.get(0));

		sponsorshipService.save(sp);

	}

	@Test
	public void searchSponsorship() {

		Collection<Sponsorship> sp = sponsorshipService.findAll();
		List<Sponsorship> spp = new ArrayList<Sponsorship>(sp);
		Sponsorship sps = spp.get(0);
		sponsorshipService.findOne(sps.getId());

	}

	@Test
	public void deleteSponsorship() {
		Collection<Sponsorship> sp = sponsorshipService.findAll();
		List<Sponsorship> spp = new ArrayList<Sponsorship>(sp);
		Sponsorship sps = spp.get(0);
		sponsorshipService.delete(sps);

		Collection<Sponsorship> collafter = sponsorshipService.findAll();
		Assert.isTrue(!collafter.contains(sps));

	}

}
