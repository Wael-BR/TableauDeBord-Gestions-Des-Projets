package tn.stage._24.gestionproet24.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import tn.stage._24.gestionproet24.entities.*;
import tn.stage._24.gestionproet24.events.TaskStatusChangeEvent;
import tn.stage._24.gestionproet24.repository.ProjectRepository;
import tn.stage._24.gestionproet24.repository.TaskRepository;
import tn.stage._24.gestionproet24.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task createTask2(Task task) {
        if (task.getUsers() != null) {
            Set<User> managedUsers = new HashSet<>();
            for (User user : task.getUsers()) {
                User managedUser = userRepository.findById(user.getId()).orElse(null);
                if (managedUser != null) {
                    managedUsers.add(managedUser);
                }
            }
            task.setUsers(managedUsers);
        }
        return taskRepository.save(task);
    }

    public Task updateTask(int id, Task taskDetails) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            // Check for status change
            Status oldStatus = task.getStatus();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setStartDate(taskDetails.getStartDate());
            task.setEndDate(taskDetails.getEndDate());
            task.setPriority(taskDetails.getPriority());

            // Save the task and publish the event if status changed
            Task updatedTask = taskRepository.save(task);
            if (!oldStatus.equals(updatedTask.getStatus())) {
                changeTaskStatus(updatedTask, updatedTask.getStatus());
            }
            return updatedTask;
        } else {
            throw new RuntimeException("Task not found with id " + id);
        }
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    public Task assignTaskToProject(int taskId, int projectId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);
        task.setProject(project);
        return taskRepository.save(task);
    }

    public Set<Task> getTasksByProjectId(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        return project.getTasks();
    }

    public void changeTaskStatus(Task task, Status newStatus) {
        Status oldStatus = task.getStatus();
        task.setStatus(newStatus);
        eventPublisher.publishEvent(new TaskStatusChangeEvent(this, task, oldStatus, newStatus));
        System.out.println("Event published for task ID: " + task.getId());

    }

}
