package tn.stage._24.gestionproet24.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.stage._24.gestionproet24.entities.Comment;
import tn.stage._24.gestionproet24.entities.Project;
import tn.stage._24.gestionproet24.entities.Task;
import tn.stage._24.gestionproet24.entities.User;
import tn.stage._24.gestionproet24.repository.ProjectRepository;
import tn.stage._24.gestionproet24.repository.TaskRepository;
import tn.stage._24.gestionproet24.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service

@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

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
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setStartDate(taskDetails.getStartDate());
            task.setEndDate(taskDetails.getEndDate());
            task.setPriority(taskDetails.getPriority());

            // You can add more fields if needed

            return taskRepository.save(task);
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
}
