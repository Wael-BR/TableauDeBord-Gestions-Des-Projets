package tn.stage._24.gestionproet24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.stage._24.gestionproet24.entities.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
}
