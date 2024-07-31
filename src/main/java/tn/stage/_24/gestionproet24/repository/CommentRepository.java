package tn.stage._24.gestionproet24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.stage._24.gestionproet24.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
