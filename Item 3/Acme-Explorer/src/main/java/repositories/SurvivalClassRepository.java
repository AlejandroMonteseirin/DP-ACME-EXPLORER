package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.SurvivalClass;


@Repository
public interface SurvivalClassRepository extends JpaRepository<SurvivalClass,Integer>{
	
	
	@Query("select s from SurvivalClass s where ?1 member of s.explorers")
	Collection<SurvivalClass> findByExplorerId(int idExplorer);
	
	@Query("select s from SurvivalClass s where ?1 not member of s.explorers")
	Collection<SurvivalClass> findByNotExplorerId(int idExplorer);
	

	@Query("select s from SurvivalClass s where s.trip.id=?1")
	Collection<SurvivalClass> getSurvivalClassesTrip(int tripId);
}
