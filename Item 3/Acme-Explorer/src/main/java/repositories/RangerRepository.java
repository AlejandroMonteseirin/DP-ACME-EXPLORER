package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Ranger;

@Repository
public interface RangerRepository extends JpaRepository<Ranger,Integer>{
	
	@Query("select r from Ranger r where r.userAccount.id = ?1")
	Ranger findByUserAccountId(int userAccountId);

	@Query("select r from Trip t join t.ranger r where t.id = ?1")
	Ranger findByTripId(int tripId);
	
	@Query("select sum(case when r.curriculum IS NOT EMPTY then 1.0 else 0.0 end)/count(r)" 
			+ " from Ranger r")
	Double getRegisteredCurriculaRatio();
	
	@Query("select sum(case when c.endorserRecords.size>=1 then 1.0 else 0.0 end)/count(r) "
			+ "from Ranger r join r.curriculum c")
	Double getEndorsedCurriculaRatio();
	
	@Query("select sum(case when r.suspicious = 1 then 1.0 else 0.0 end)/count(r) "
			+ " from Ranger r")
	Double getSuspiciousRangersRatio();
	
	@Query("select r from Ranger r join r.curriculum c where c.id = ?1")
	Ranger getRangerByCurriculumId(int curriculumId);
	
	@Query("select r from Ranger r where r.suspicious = 1")
	Collection<Ranger> getSuspiciousRangers();
	
}
