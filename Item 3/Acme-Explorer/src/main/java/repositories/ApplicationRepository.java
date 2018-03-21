
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select avg(t.applications.size),min(t.applications.size),max(t.applications.size),sqrt(sum(t.applications.size*t.applications.size)/count(t.applications.size)-(avg(t.applications.size)*avg(t.applications.size))) from Trip t")
	Double[] computeAvgMinMaxStdvPerTrip();

	@Query("select sum(case when a.status = 'PENDING' " + "then 1.0 else 0.0 end)/count(a) as pendingStatus_ratio from Application a")
	Double getPendingRatio();

	@Query("select sum(case when a.status = 'DUE' " + "then 1.0 else 0.0 end)/count(a) as dueStatus_ratio from Application a")
	Double getDueRatio();

	@Query("select sum(case when a.status = 'ACCEPTED' " + "then 1.0 else 0.0 end)/count(a) as acceptedStatus_ratio from Application a")
	Double getAcceptedRatio();

	@Query("select sum(case when a.status = 'CANCELLED' " + "then 1.0 else 0.0 end)/count(a) as cancelledStatus_ratio from Application a")
	Double getCancelledRatio();

	@Query("select a from Explorer e join e.applications a where e.id = ?1 group by a.status")
	Collection<Application> getExplorerApplicationsByStatus(int explorerId);

	@Query("select a from Manager m join m.trips t join t.applications a where m.id = ?1")
	Collection<Application> getManagerTripsApplications(int managerId);

	@Query("select a from Explorer e join e.applications a where e.id = ?1")
	Collection<Application> getExplorerApplications(int explorerId);

	@Query("select distinct a.status from Explorer e join e.applications a where e.id = ?1")
	Collection<String> getSetOfStatus(int explorerId);

	@Query("select a from Explorer e join e.applications a where e.id = ?1 and a.status= ?2")
	Collection<Application> getApplicationsByStatusAndExplorerId(int explorerId, String status);

}
