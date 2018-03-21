
package repositories;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

//	@Query("select new map (t,(CASE WHEN EXISTS (SELECT t FROM t.trip) THEN 1 ELSE 0 END)) from LegalText t group by t.id")
//	List<Map<LegalText, Integer>> countLegalTextReferenced();

	@Query("select l from LegalText l where l.savedMode = 'FINAL MODE'")
	Collection<LegalText> getFinalModeLegalTexts();

}
