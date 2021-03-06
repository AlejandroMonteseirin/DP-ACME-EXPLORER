package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='out box'")
	Folder getOutBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='in box'")
	Folder getInBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='spam box'")
	Folder getSpamBoxFolderFromActorId(int id);

	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='trash box'")
	Folder getTrashBoxFolderFromActorId(int id);
	
	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name='notification box'")
	Folder getNotificationBoxFolderFromActorId(int id);
	

	// @Query("select f from Folder f join f.messages m where m.id = ?1")
	// Collection<Folder> getMessageFolderFromMessageId(int id);

	// @Query("select f from Folder f join f.messages m where m.id=?1 and f.actor.id=?2")
	// Folder getFolderFromMessageAndActorId(int messageId, int actorId);
	@Query("select f from Folder f where f.actor.id=?1 and f.parentFolder=null")
	Collection<Folder> getFirstLevelFoldersFromActorId(int actorId);

	@Query("select f from Folder f join f.messages m where m.id=?1")
	Folder getFolderFromMessageId(int messageId);
	
	@Query("select f from Folder f where f.parentFolder.id=?1")
	Collection<Folder> getChildFolders(int folderId);
	
	

}
