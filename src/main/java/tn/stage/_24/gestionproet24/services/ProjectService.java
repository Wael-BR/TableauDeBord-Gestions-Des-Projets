package tn.stage._24.gestionproet24.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import tn.stage._24.gestionproet24.entities.*;
import tn.stage._24.gestionproet24.entities.listeners.ProjectStatusHistory;
import tn.stage._24.gestionproet24.events.ProjectStatusChangeEvent;
import tn.stage._24.gestionproet24.exceptions.ResourceNotFoundException;
import tn.stage._24.gestionproet24.repository.ProjectRepository;
import tn.stage._24.gestionproet24.repository.TaskRepository;
import tn.stage._24.gestionproet24.repository.UserRepository;
import tn.stage._24.gestionproet24.repository.listeners.ProjectStatusHistoryRepository;

import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectStatusHistoryRepository projectStatusHistoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ProjectService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository,
            ProjectStatusHistoryRepository projectStatusHistoryRepository,
            ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectStatusHistoryRepository = projectStatusHistoryRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(int id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        Project savedProject = projectRepository.save(project);

        // Create initial status history
        ProjectStatusHistory history = new ProjectStatusHistory();
        history.setProject(savedProject);
        history.setOldStatus(null); // No old status for a new project
        history.setNewStatus(savedProject.getStatus());
        history.setOldRespectBudget(null); // No old respect budget for a new project
        history.setNewRespectBudget(savedProject.getRespectBudget());
        history.setOldRespectPlanning(null); // No old respect planning for a new project
        history.setNewRespectPlanning(savedProject.getRespectPlanning());
        history.setOldRespectPerimetre(null); // No old respect perimetre for a new project
        history.setNewRespectPerimetre(savedProject.getRespectPerimetre());
        history.setOldSanteGenerale(null); // No old sante generale for a new project
        history.setNewSanteGenerale(savedProject.getSanteGenerale());
        history.setChangeDate(new Date());
        history.setAssignedUser(savedProject.getAssignedUser()); // Set if needed

        // Save the initial status history
        projectStatusHistoryRepository.save(history);

        return savedProject;
    }

    public Project updateProject(int id, Project projectDetails) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            // Capture old values
            Status oldStatus = project.getStatus();
            RespectBudget oldRespectBudget = project.getRespectBudget();
            Avancement oldRespectPlanning = project.getRespectPlanning();
            Avancement oldRespectPerimetre = project.getRespectPerimetre();
            SanteGenerale oldSanteGenerale = project.getSanteGenerale();

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
            project.setDateLivraison(projectDetails.getDateLivraison());
            project.setPriority(projectDetails.getPriority());
            project.setType(projectDetails.getType());
            project.setBudget(projectDetails.getBudget());
            project.setActualBudget(projectDetails.getActualBudget());

            Project updatedProject = projectRepository.save(project);

            // Publish an event if any relevant attribute has changed
            if (!oldStatus.equals(updatedProject.getStatus()) ||
                    !oldRespectBudget.equals(updatedProject.getRespectBudget()) ||
                    !oldRespectPlanning.equals(updatedProject.getRespectPlanning()) ||
                    !oldRespectPerimetre.equals(updatedProject.getRespectPerimetre()) ||
                    !oldSanteGenerale.equals(updatedProject.getSanteGenerale())) {

                eventPublisher.publishEvent(new ProjectStatusChangeEvent(
                        this,
                        updatedProject,
                        oldStatus,
                        updatedProject.getStatus(),
                        oldRespectBudget,
                        updatedProject.getRespectBudget(),
                        oldRespectPlanning,
                        updatedProject.getRespectPlanning(),
                        oldRespectPerimetre,
                        updatedProject.getRespectPerimetre(),
                        oldSanteGenerale,
                        updatedProject.getSanteGenerale()
                ));
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

    /******************** progress *********************/
    public float calculateProgressPercentage(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Set<Task> tasks = project.getTasks();
        if (tasks.isEmpty()) {
            return 0;
        }

        long totalTasks = tasks.size();
        long completedTasks = tasks.stream()
                .filter(task -> task.getStatus() == Status.LIVRE_ET_CLOTURE || task.getStatus() == Status.LIVRE)
                .count();

        return (completedTasks / (float) totalTasks) * 100;
    }

}


