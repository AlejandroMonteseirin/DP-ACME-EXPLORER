package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;

@Service
@Transactional
public class AuditService {

	// Managed repository
	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private AuditorService auditorService;
	@Autowired
	private AdministratorService administratorService;

	// Constructor
	public AuditService() {
		super();
	}

	// Simple CRUD methods
	// B.14.2 Un Auditor puede escribir(crear) un audit
	public Audit create() {
		final Auditor auditor = this.auditorService.findByPrincipal();
		Audit a;
		Date moment;
		Collection<Audit> audits;
		Collection<String> URLs;

		a = new Audit();
		moment = new Date(System.currentTimeMillis() - 1);
		audits = auditor.getAudits();
		URLs = new ArrayList<String>();

		audits.add(a);
		a.setAuditor(auditor);
		a.setAuditionMoment(moment);
		a.setAttachmentURLs(URLs);

		// Se asocia aquí el audit a su auditor y se llama al save
		auditor.setAudits(audits);
		this.auditorService.save(auditor);

		return a;
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);

		Audit a;
		Date moment;

		moment = new Date(System.currentTimeMillis());
		audit.setAuditionMoment(moment);
		a = this.auditRepository.save(audit);

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(audit.getTitle());

		for (final String url : audit.getAttachmentURLs())
			this.administratorService.checkIsSpam(url);

		this.administratorService.checkIsSpam(audit.getDescription());

		return a;
	}

	public void delete(final Audit audit) {
		this.checkPrincipal(audit);
		Assert.notNull(audit);

		this.auditRepository.delete(audit);
	}

	public Audit findOne(final int auditId) {
		Assert.notNull(auditId);

		Audit a;

		a = auditRepository.findOne(auditId);

		return a;
	}

	public Audit findOneToEdit(final int auditId) {
		Assert.notNull(auditId);

		Audit a;

		a = auditRepository.findOne(auditId);

		checkPrincipal(a);

		return a;
	}

	public Collection<Audit> findAll() {
		return this.auditRepository.findAll();
	}

	// Other business methods
	public void checkPrincipal(final Audit audit) {
		Auditor auditor;
		Auditor actor;

		auditor = audit.getAuditor();
		actor = this.auditorService.findByPrincipal();

		Assert.isTrue(auditor.equals(actor));
		Assert.isTrue(audit.getSavedMode().equals("DRAFT MODE"));
	}

	public void checkPrincipal2(final Audit audit) {
		Auditor auditor;
		Auditor actor;

		auditor = audit.getAuditor();
		actor = this.auditorService.findByPrincipal();

		Assert.isTrue(auditor.equals(actor));
		// Assert.isTrue(audit.getSavedMode().equals("DRAFT MODE"));
	}

	public Collection<Audit> auditsFromTrip(final int tripId) {
		Collection<Audit> result;
		result = this.auditRepository.auditsFromTrip(tripId);
		return result;

	}

	// Con el siguiente metodo sacamos las estadisticas para el dashboard

	public Double[] getMinMaxAvgStddevAuditsPerTrip() {
		Double[] result;
		result = this.auditRepository.getMinMaxAvgStddevAudisPerTrip();
		return result;
	}

	public Double auditRecordRatio() {
		Double result;
		result = this.auditRepository.getTripsAuditRecord();
		return result;
	}

	public Collection<Audit> getAuditsByAuditorId(final int auditorId) {
		return this.auditRepository.getAuditsByAuditorId(auditorId);
	}

}
