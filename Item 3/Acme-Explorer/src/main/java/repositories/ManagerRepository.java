package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;



@Repository
public interface ManagerRepository extends JpaRepository<Manager,Integer>{
	
	@Query("select sum(case when m.suspicious = 1 then 1.0 else 0.0 end)/count(m)from Manager m")
	Double getSuspiciousManagersRatio();
	
	@Query("select m from Manager m where m.suspicious = 1")
	Collection<Manager> getSuspiciousManagers();
	
	@Query("select m from Manager m join m.trips t join t.applications a where a.id= ?1")
	Manager getManagerFromApplicationId(int applicationId);
//	 esto no funsiona jeje
//	@Query("select m from Manager m where ?1 member of s.survivalClasses")
//	Collection<Manager> findBySurvivalClassId(int idSurvivalClass);

	@Query("select a from Manager a where a.userAccount.id = ?1")
	Manager findByUserAccountId(int id);
}
