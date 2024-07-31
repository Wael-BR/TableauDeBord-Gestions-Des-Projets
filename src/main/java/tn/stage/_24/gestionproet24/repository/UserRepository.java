package tn.stage._24.gestionproet24.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.stage._24.gestionproet24.entities.Role;
import tn.stage._24.gestionproet24.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsAdminByNom(String nom);

    @EntityGraph(attributePaths = {"comments"}) // Eager fetch comments
    Optional<User> findById(Long id);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
    List<User> findUserByRole(Role role);
    /*@Query("SELECT u FROM User u JOIN u.projects p WHERE p.id = :projectId AND u.role IN ('DEVELOPER', 'TESTER', 'DESIGNER')")*/
    @Query("SELECT u FROM User u JOIN u.projects p WHERE p.id = :projectId ")
    List<User> findUsersByProjectIdAndRoles(@Param("projectId") Long projectId);
    @Query("select  a from User a where a.worken= false ")
    Optional<List<User>> findBlockedAccount();

    @Modifying
    @Transactional
    @Query("UPDATE User a set a.worken=true where a.id= :idU")
    void updateWorkenToTure(@Param("idU") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u set u.worken=false where u.id= :idU")
    void updateWorkenToFalse(@Param("idU") Long id);



}
