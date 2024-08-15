package tn.stage._24.gestionproet24.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import tn.stage._24.gestionproet24.entities.*;
import tn.stage._24.gestionproet24.events.ProjectStatusChangeEvent;
import tn.stage._24.gestionproet24.repository.ProjectRepository;
import tn.stage._24.gestionproet24.repository.TaskRepository;
import tn.stage._24.gestionproet24.repository.UserRepository;

import java.util.Optional;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ProjectService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(int id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(int id, Project projectDetails) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Status oldStatus = project.getStatus(); // Capture the old status

            // Update the project fields
            project.setNom(projectDetails.getNom());
            project.setDescription(projectDetails.getDescription());
            project.setStatus(projectDetails.getStatus());
            project.setSanteGenerale(projectDetails.getSanteGenerale());
            project.setRespectPlanning(projectDetails.getRespectPlanning());
            project.setRespectPerimetre(projectDetails.getRespectPerimetre());
            project.setRespectBudget(projectDetails.getRespectBudget());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            project.setPriority(projectDetails.getPriority());
            project.setType(projectDetails.getType());
            project.setBudget(projectDetails.getBudget());

            Project updatedProject = projectRepository.save(project);

            // Publish an event if the status has changed
            if (!oldStatus.equals(updatedProject.getStatus())) {
                eventPublisher.publishEvent(new ProjectStatusChangeEvent(this, updatedProject, oldStatus, updatedProject.getStatus()));
            }

            return updatedProject;
        } else {
            throw new RuntimeException("Project not found with id " + id);
        }
    }

    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }

    public Project addTaskToProject(int projectId, Task task) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (!projectOpt.isPresent()) {
            throw new RuntimeException("Project not found");
        }

        Project project = projectOpt.get();
        task.setProject(project); // Set the project reference in the task
        project.getTasks().add(task); // Add the task to the project’s task list

        taskRepository.save(task); // Save the task to the database
        return projectRepository.save(project); // Save the updated project to the database
    }
}
