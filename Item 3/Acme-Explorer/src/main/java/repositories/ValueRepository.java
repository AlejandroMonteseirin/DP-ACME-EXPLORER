package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Value;

@Repository
public interface ValueRepository extends JpaRepository<Value, Integer> {
	
	@Query("select v from Value v where v.tag.id = ?1")
	Collection<Value> getValuesByTagId(int tagId);
	
}
