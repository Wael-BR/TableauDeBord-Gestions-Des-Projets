package tn.stage._24.gestionproet24.entitiesListener;

import jakarta.persistence.PreUpdate;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tn.stage._24.gestionproet24.entities.*;
import tn.stage._24.gestionproet24.entities.listeners.CommentHistory;
import tn.stage._24.gestionproet24.entities.listeners.ProjectStatusHistory;
import tn.stage._24.gestionproet24.entities.listeners.TaskStatusHistory;
import tn.stage._24.gestionproet24.repository.listeners.*;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class EntityChangeListener {

    private final ProjectStatusHistoryRepository projectStatusHistoryRepository;
    private final TaskStatusHistoryRepository taskStatusHistoryRepository;
    private final CommentHistoryRepository commentHistoryRepository;

    @PreUpdate
    public void onProjectPreUpdate(Project project) {
        // Save old status, assigned user, etc. before the update
        Status oldStatus = project.getStatus(); // Assume this gets the current status before update
        project.setOldStatus(oldStatus);
    }

    @PostUpdate
    @Transactional
    public void onProjectPostUpdate(Project project) {
        // Save the status change in the ProjectStatusHistory
        ProjectStatusHistory history = new ProjectStatusHistory();
        history.setProject(project);
        history.setOldStatus(project.getOldStatus()); // This should be set in onProjectPreUpdate
        history.setNewStatus(project.getStatus());
        history.setChangeDate(new Date());
        history.setAssignedUser(project.getAssignedUser()); // Ensure this method is available

        projectStatusHistoryRepository.save(history);
    }

    @PreUpdate
    public void onTaskPreUpdate(Task task) {
        // Save old status, assigned user, etc. before the update
        Status oldStatus = task.getStatus(); // Assume this gets the current status before update
        task.setOldStatus(oldStatus);
    }

    @PostUpdate
    @Transactional
    public void onTaskPostUpdate(Task task) {
        // Save the status change in the TaskStatusHistory
        TaskStatusHistory history = new TaskStatusHistory();
        history.setTask(task);
        history.setOldStatus(task.getOldStatus()); // This should be set in onTaskPreUpdate
        history.setNewStatus(task.getStatus());
        history.setChangeDate(new Date());
        history.setAssignedUser(task.getAssignedUser()); // Assuming there's a method to get assigned user

        taskStatusHistoryRepository.save(history);
    }

    @PreUpdate
    public void onCommentPreUpdate(Comment comment) {
        // Save content, author, assigned user, etc. before the update
        comment.setOldContent(comment.getContent());
    }

    @PostUpdate
    @Transactional
    public void onCommentPostUpdate(Comment comment) {
        // Save the comment change in the CommentHistory
        CommentHistory history = new CommentHistory();
        history.setComment(comment);
        history.setContent(comment.getOldContent());
        history.setDate(new Date());
        history.setAuthor(comment.getAuthor());
        history.setAssignedUser(comment.getAssignedUser()); // Assuming there's a method to get assigned user

        commentHistoryRepository.save(history);
    }
}
