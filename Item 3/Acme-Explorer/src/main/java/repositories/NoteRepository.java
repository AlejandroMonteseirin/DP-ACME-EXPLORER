
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	//Query B/1:
	//The minimum, the maximum, the average, and the standard deviation of the number of notes per trip.

	@Query("select min(t.notes.size), max(t.notes.size), avg(t.notes.size), stddev(t.notes.size) from Trip t")
	Double[] getMinMaxAvgStddevNotesPerTrip();

	@Query("select n from Manager m join m.trips t join t.notes n where m.id=?1")
	Collection<Note> getNotesToManagerTrips(int managerId);

	@Query("select n from Auditor a join a.notes n where a.id= ?1")
	Collection<Note> getNotesByAuditorId(int auditorId);
}
