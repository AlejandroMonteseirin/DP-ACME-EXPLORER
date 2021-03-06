package repositories;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Trip;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer>{
	
	@Query("select f from Finder f where f.explorer.id = ?1")
	Finder getFinderByExplorerId(int explorerId);
	

}
