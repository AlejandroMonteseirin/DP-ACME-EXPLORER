package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Note;
import domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer>{

}
