
package services;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private CreditCardService creditCardService;

	// Constructor
	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods
	public Sponsorship create() {
		Sponsorship result;
		Sponsor s;
		
		result = new Sponsorship();
		s = (Sponsor) actorService.findByPrincipal();
		
		result.setSponsor(s);
		
		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsorship result;
		CreditCard ccSaved;
		
		ccSaved = creditCardService.save(sponsorship.getCreditCard());
		
		sponsorship.setCreditCard(ccSaved);
		result = this.sponsorshipRepository.save(sponsorship);
		
		//Comprobamos si es spam
		administratorService.checkIsSpam(sponsorship.getBannerURL());
		administratorService.checkIsSpam(sponsorship.getInfoPageLink());
		
		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		this.sponsorshipRepository.delete(sponsorship);
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship result;
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		return result;
	}
	
	public Sponsorship findOneToEdit(final int sponsorshipId) {
		Sponsorship result;
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		checkPrincipal(result);
		return result;
	}
	
	//Other business methods
	public void checkPrincipal(Sponsorship s){
		Sponsor sponsor;
		
		sponsor = (Sponsor) actorService.findByPrincipal();
		
		Assert.isTrue(sponsor.getSponsorships().contains(s));
	}
	
	public Sponsorship randomSponsorship(){
		Sponsorship sponsorship = null;
		
		int size = this.findAll().size();
		int item = new Random().nextInt(size);
		int i = 0;
		for(Sponsorship s : this.findAll()){
		    if (i == item){
		        sponsorship = s;
		    }
		    i++;
		}
		return sponsorship;
	}

}
