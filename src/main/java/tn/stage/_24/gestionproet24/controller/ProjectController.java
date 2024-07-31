package tn.stage._24.gestionproet24.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.stage._24.gestionproet24.entities.Project;
import tn.stage._24.gestionproet24.entities.Task;
import tn.stage._24.gestionproet24.entities.User;
import tn.stage._24.gestionproet24.services.AccountService;
import tn.stage._24.gestionproet24.services.ProjectService;
import tn.stage._24.gestionproet24.services.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/api/projects")
public class ProjectController {

    private ProjectService projectService;
    private AccountService accountService;
    private TaskService taskService;

    @GetMapping("/GetAllAccounts")
    public List<User> getAllAccount() {
        return accountService.getAllAccount();
    }

    @GetMapping("/GetAllProjects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/GetProjectById/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/AddProject")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/UpdateProject/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody Project projectDetails) {
        try {
            Project updatedProject = projectService.updateProject(id, projectDetails);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteProject/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/AddTaskToProject/{projectId}")
    public ResponseEntity<Project> addTaskToProject(@PathVariable int projectId, @RequestBody Task task) {
        try {
            Project updatedProject = projectService.addTaskToProject(projectId, task);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/GetTasksByProject/{projectId}")
    public Set<Task> getTasksByProject(@PathVariable int projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

}
