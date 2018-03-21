
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from Trip t")
	Collection<Trip> getAllTrips();

	@Query("select t from Trip t join t.category c where c.id = ?1")
	Collection<Trip> getTripsByCategory(int categoryId);

	@Query("select sum(s.price) from Trip t join t.stages s where t.id=?1")
	Double getStagesPrice(int id);

	//@Query("select t from Trip t where t.title || t.ticker || t.description like '?1'")
	@Query("select t from Trip t where t.title like CONCAT('%',?1,'%') or t.description like CONCAT('%',?1,'%') or t.ticker like CONCAT('%',?1,'%') and t.publicationDate >= CURRENT_DATE")
	Collection<Trip> getTripsByKeyWord(String keyWord);

	//	@Query("select count(t) from Trip t join t.tags tg where tg.id=?1")
	//	Integer getReferencedTripsNumberByTagId(int tagId);

	@Query("select sum(case when t.audits.size > 0 then 1.0 else 0.0 end)/count(DISTINCT t) as " + "Audits_ratio from Trip t")
	Double getAuditedTrips();

	@Query("select count(a) from Explorer e join e.applications a where a.trip.id = ?1 and e.id = ?2 and a.status = 'ACCEPTED'")
	Integer getAcceptedApplicationsByExplorerId(int explorerId, int tripId);

	// Queries estadisticas dashboard
	// The average, the minimum, the maximum, and the standard deviation of the number of trips managed per manager.
	@Query("select avg(m.trips.size),min(m.trips.size),max(m.trips.size),sqrt(sum(m.trips.size*m.trips.size)/count(m.trips.size)-(avg(m.trips.size)*avg(m.trips.size))) from Manager m")
	Double[] computeAvgMinMaxStdvPerManager();

	// The average, the minimum, the maximum, and the standard deviation of the price of the trips.
	@Query("select avg(t.price), min(t.price), max(t.price), sqrt(sum(t.price * t.price) / count(t.price) - (avg(t.price) * avg(t.price))) from Trip t")
	Double[] computeAvgMinMaxStdvPerPrice();

	// The average, the minimum, the maximum, and the standard deviation of the number trips guided per ranger.
	@Query("select avg(r.trips.size),max(r.trips.size),min(r.trips.size),sqrt(sum(r.trips.size * r.trips.size) / count(r.trips.size) - (avg(r.trips.size) * avg(r.trips.size))) from Ranger r")
	Double[] computeAvgMaxMinStdvPerRanger();

	// The ratio of trips that have been cancelled versus the total number of trips that have been organised.
	@Query("select sum(case when t.status = 'CANCELLED' then 1.0 else 0.0 end)/count(t) as cancelledTrip_ratio from Trip t")
	Double ratioTripsCancelled();

	// The listing of trips that have got at least 10% more applications than the average, ordered by number of applications.
	@Query("select t from Trip t where t.applications.size >= 1.1*(select avg(t.applications.size) from Trip t) order by t.applications.size")
	Collection<Trip> tripsMoreAplications();

	@Query("select t from Trip t where t.price <= ?2 and t.price >= ?1 and t.startDate >= ?3 and t.endDate <= ?4 and t.publicationDate <= CURRENT_DATE and (t.title like CONCAT('%',?5,'%') or t.description like CONCAT('%',?5,'%') or t.ticker like CONCAT('%',?5,'%'))")
	Collection<Trip> findTripsBySearchCriteria(Double minPrice, Double maxPrice, Date startDate, Date endDate, String keyword);

	@Query("select t from Trip t where t.endDate <= CURRENT_DATE")
	Collection<Trip> getEndedTrips();

	@Query("select t from Trip t where t.publicationDate <= CURRENT_DATE")
	Collection<Trip> getVisibleTrips();
	
	@Query("select t from Trip t where t.publicationDate <= CURRENT_DATE and t.status = 'ACTIVE'")
	Collection<Trip> getVisibleAndActiveTrips();

	@Query("select t from Trip t where t.publicationDate <= CURRENT_DATE and t.category.id = ?1")
	Collection<Trip> getVisibleTripsByCategory(int categoryId);

	//		Obtenemos todos los trips publicados usando el repositorio paginado [A+]
	@Query("select t from Trip t where t.publicationDate <= CURRENT_DATE and t.status = 'ACTIVE'")
	Page<Trip> getVisibleTripsPaginate(Pageable p);

	@Query("select t from Trip t join t.applications a where a.id = ?1")
	Trip getTripByApplicationId(int applicationId);

	@Query("select t from Manager m join m.trips t where m.id = ?1")
	Collection<Trip> getTripsByManagerId(int managerId);
	
	@Query("select t from Manager m join m.trips t where m.id = ?1")
	Page<Trip> getTripsByManagerIdPageable(Pageable p, int managerId);

	@Query("select t from Trip t where t.publicationDate > CURRENT_DATE")
	Collection<Trip> getNotPublishedTrips();

	@Query("select a.trip from Explorer e join e.applications a where e.id = ?1")
	Collection<Trip> getExplorerApplicationTrips(int explorerId);

	@Query("select t from Trip t join t.values v where v.id = ?1")
	Collection<Trip> getTripsByValue(int valueId);

	@Query("select t from Explorer e join e.applications a join a.trip t where e.id = ?1 and a.status = 'ACCEPTED' and t.endDate<= CURRENT_DATE")
	Collection<Trip> getAcceptedTrips(int explorerId);
	
	
	//relacionado con survival Class
	@Query("select y from Explorer e join e.applications x join x.trip y where x.status = 'ACCEPTED' and e.id=?1")
	Collection<Trip> getTripsExplorerApplication(int explorerId);
	

}
