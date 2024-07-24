package tn.stage._24.gestionproet24.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.stage._24.gestionproet24.dao.StadersResponse;
import tn.stage._24.gestionproet24.entities.User;
import tn.stage._24.gestionproet24.services.AccountService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/")
public class AccountController {
        private final AccountService accountService;

        @PutMapping("/allowAccountToWork/{idAcc}")
        public ResponseEntity<StadersResponse> allowAccountToWork(
                @PathVariable ("idAcc") Long id
        ){
                return this.accountService.allowAccountWork(id);
        }

        @GetMapping("/GetAllAccounts")
        public List<User> getAllAccount() {
                return accountService.getAllAccount();
        }

        @GetMapping("/GetOneAccount/{id}")
        public Optional<User> getAccountById(@PathVariable Long id) {
                return accountService.getAccountById(id);
        }
        @PutMapping("/common/UpdateAccount/{id}")
        public User updateAccount(@PathVariable Long id, @RequestBody User updatedAccount) {
                return accountService.updateAccount(id, updatedAccount);
        }

        @DeleteMapping("/DeleteAccount/{id}")
        public void deleteAccount(@PathVariable Long id) {
                accountService.deleteAccount(id);
        }

        @GetMapping("/GetAllAccountBlocked")
        public Optional<List<User>> endPointA(){
                return this.accountService.getAllAccountsBlokced();
        }

        @PutMapping("/AllowUserToNavigate/{id}")
        public void endPointA(@PathVariable Long id){

                accountService.updateAccountBlockedToAllowWorkenAsAnAdmin(id);
        }

        @PutMapping("/BlockUserToNavigate/{id}")
        public void endPointB(@PathVariable Long id){
                accountService.updateAccountAllowedWorkenToBlockedAsAnAdmin(id);
        }


        @PostMapping("/AffectUserToProject/{userId}/assign/{projectId}")
        public ResponseEntity<User> assignUserToProject(@PathVariable Long userId, @PathVariable int projectId) {
                try {
                        User updatedUser = accountService.assignUserToProject(userId, projectId);
                        return ResponseEntity.ok(updatedUser);
                } catch (RuntimeException e) {
                        return ResponseEntity.badRequest().body(null);
                }
        }

        @PostMapping("/AffectUserToTasks/{userId}/assign/{taskId}")
        public ResponseEntity<User> assignUserToTasks(@PathVariable Long userId, @PathVariable int taskId) {
                try {
                        User updatedUser = accountService.assignUserToTasks(userId, taskId);
                        return ResponseEntity.ok(updatedUser);
                } catch (RuntimeException e) {
                        return ResponseEntity.badRequest().body(null);
                }
        }

        @PutMapping("/addUserAndAssignToComment/{commentId}")
        public User addUserAndAssignToComment(@RequestBody User user, @PathVariable("commentId") int commentId){
                return  accountService.addUserAndAssignToComment(user,commentId);
        }

        @GetMapping("/admins")
        public List<User> getAdmins(){
                return accountService.getAdminUsers();
        }

}
