
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select min(t.audits.size), max(t.audits.size), avg(t.audits.size), stddev(t.audits.size) from Trip t")
	Double[] getMinMaxAvgStddevAudisPerTrip();

	@Query("select a from Audit a where a.trip.id = ?1")
	Collection<Audit> auditsFromTrip(int tripId);

	@Query("select a from Audit a where a.auditor.id = ?1")
	Collection<Audit> getAuditsByAuditorId(int auditorId);

	@Query("select sum(case when t.audits.size>=1 then 1.0 else 0.0 end)/count(t) " + "from Trip t")
	Double getTripsAuditRecord();

}
