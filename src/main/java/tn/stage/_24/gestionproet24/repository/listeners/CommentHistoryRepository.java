package tn.stage._24.gestionproet24.repository.listeners;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.stage._24.gestionproet24.entities.listeners.CommentHistory;

public interface CommentHistoryRepository extends JpaRepository<CommentHistory, Long> {
}
