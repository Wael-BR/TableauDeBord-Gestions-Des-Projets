package tn.stage._24.gestionproet24.repository.listeners;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.stage._24.gestionproet24.entities.listeners.TaskStatusHistory;

public interface TaskStatusHistoryRepository extends JpaRepository<TaskStatusHistory, Long> {
}
