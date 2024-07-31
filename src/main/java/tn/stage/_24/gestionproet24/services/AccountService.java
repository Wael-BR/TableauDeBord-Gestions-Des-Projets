package tn.stage._24.gestionproet24.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.stage._24.gestionproet24.dao.StadersResponse;
import tn.stage._24.gestionproet24.entities.*;
import tn.stage._24.gestionproet24.repository.CommentRepository;
import tn.stage._24.gestionproet24.repository.ProjectRepository;
import tn.stage._24.gestionproet24.repository.TaskRepository;
import tn.stage._24.gestionproet24.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<StadersResponse> allowAccountWork(Long idAccount){
        try {
            Optional<User> userOptional = this.userRepository.findById(idAccount);
            if (userOptional.isEmpty()){
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(StadersResponse.builder().message("User not Found").build());
            }else {
                if(userOptional.get().getWorken()) {
                    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(StadersResponse.builder().message("Account already on work.").build());
                }
                userOptional.get().setWorken(true);
                this.userRepository.save(userOptional.get());
                return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(StadersResponse.builder().message("Affected with success").build());
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(StadersResponse.builder().message("Internal server ERROR").build());
        }
    }

    public User updateAccount(Long id, User updatedAccount) {
        Optional<User> existingAccount = userRepository.findById(id);
        String Alpha = updatedAccount.getPassword();
        if (existingAccount.isPresent()) {
            User accountToUpdate = existingAccount.get();
            accountToUpdate.setUsername(updatedAccount.getUsername());
            accountToUpdate.setGrade(updatedAccount.getGrade());
            accountToUpdate.setNom(updatedAccount.getNom());
            accountToUpdate.setEmail(updatedAccount.getEmail());
            accountToUpdate.setPassword(passwordEncoder.encode(Alpha));


            // You can update other properties as needed
            return userRepository.save(accountToUpdate);
        } else {
            throw new RuntimeException("Account not found with id: " + id);
        }
    }

    public List<User> getAllAccount() {
        return userRepository.findAll();
    }



    public Optional<User> getAccountById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteAccount(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<List<User>> getAllAccountsBlokced(){
        return this.userRepository.findBlockedAccount();
    }

    public void updateAccountBlockedToAllowWorkenAsAnAdmin(Long id){
        this.userRepository.updateWorkenToTure(id);
    }

    public void updateAccountAllowedWorkenToBlockedAsAnAdmin(Long id){
        this.userRepository.updateWorkenToFalse(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Transactional
    public User assignUserToProject(Long userId, int projectId) {
        /*Optional<User> userOptional = userRepository.findById(userId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (userOptional.isPresent() && projectOptional.isPresent()) {
            User user = userOptional.get();
            Project project = projectOptional.get();

            user.getProjects().add(project);
            project.getUsers().add(user);

            userRepository.save(user);
            System.out.println(userId + projectId);
            return user;
        } else {
            throw new RuntimeException("User or Project not found");
        }*/
        User user = userRepository.findById(userId).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);
        try {
            user.getProjects().add(project);
        } catch (NullPointerException exception) {
            Set<Project> projectList = new HashSet<>();
            projectList.add(project);
            user.setProjects(projectList);
        }

        return userRepository.save(user);
    }

    @Transactional
    public User assignUserToTasks(Long userId, int taskId) {

        /*User user = userRepository.findById(userId).orElse(null);
        Task task = taskRepository.findById(taskId).orElse(null);
        try {
            user.getTasks().add(task);
        } catch (NullPointerException exception) {
            Set<Task> tasksList = new HashSet<>();
            tasksList.add(task);
            user.setTasks(tasksList);
        }

        return userRepository.save(user);*/
        User user = userRepository.findById(userId).orElse(null);
        Task task = taskRepository.findById(taskId).orElse(null);

        if (user == null || task == null) {
            throw new RuntimeException("User or Task not found");
        }

        try {
            user.getTasks().add(task);
        } catch (NullPointerException exception) {
            Set<Task> taskList = new HashSet<>();
            taskList.add(task);
            user.setTasks(taskList);
        }

        return userRepository.save(user);
    }

    public User addUserAndAssignToComment(User user, int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        comment.setAuthor(user); // Set the user as the author of the comment

        if (user.getComments() == null) {
            user.setComments(new HashSet<>());
        }
        user.getComments().add(comment); // Add comment to user's comments

        return userRepository.save(user);
    }


    public List<User> getAdminUsers() {
        return userRepository.findUserByRole(Role.ADMIN);
    }

    // AccountService.java
    public List<User> getNonAdminUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !"ADMIN".equals(user.getRole())) // Filter out admins
                .collect(Collectors.toList());
    }

    public List<User> getUsersByProject(Long projectId) {
        return userRepository.findUsersByProjectIdAndRoles(projectId);
    }

}
