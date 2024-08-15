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
import tn.stage._24.gestionproet24.repository.listeners.ProjectStatusHistoryRepository;
import tn.stage._24.gestionproet24.repository.listeners.TaskStatusHistoryRepository;
import tn.stage._24.gestionproet24.repository.listeners.CommentHistoryRepository;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class EntityChangeListener {

    private final ProjectStatusHistoryRepository projectStatusHistoryRepository;
    private final TaskStatusHistoryRepository taskStatusHistoryRepository;
    private final CommentHistoryRepository commentHistoryRepository;

    @PreUpdate
    public void onProjectPreUpdate(Project project) {
        // Capture old values before updating
        project.setOldStatus(project.getStatus());
        project.setOldRespectBudget(project.getRespectBudget());
        project.setOldRespectPlanning(project.getRespectPlanning());
        project.setOldRespectPerimetre(project.getRespectPerimetre());
        project.setOldSanteGenerale(project.getSanteGenerale());
    }

    @PostUpdate
    @Transactional
    public void onProjectPostUpdate(Project project) {
        ProjectStatusHistory history = new ProjectStatusHistory();
        history.setProject(project);
        history.setOldStatus(project.getOldStatus());
        history.setNewStatus(project.getStatus());
        history.setOldRespectBudget(project.getOldRespectBudget());
        history.setNewRespectBudget(project.getRespectBudget());
        history.setOldRespectPlanning(project.getOldRespectPlanning());
        history.setNewRespectPlanning(project.getRespectPlanning());
        history.setOldRespectPerimetre(project.getOldRespectPerimetre());
        history.setNewRespectPerimetre(project.getRespectPerimetre());
        history.setOldSanteGenerale(project.getOldSanteGenerale());
        history.setNewSanteGenerale(project.getSanteGenerale());
        history.setChangeDate(new Date());
        history.setAssignedUser(project.getAssignedUser());

        projectStatusHistoryRepository.save(history);
    }

    @PreUpdate
    public void onTaskPreUpdate(Task task) {
        task.setOldStatus(task.getStatus());
        // Save other old fields if necessary
    }

    @PostUpdate
    @Transactional
    public void onTaskPostUpdate(Task task) {
        TaskStatusHistory history = new TaskStatusHistory();
        history.setTask(task);
        history.setOldStatus(task.getOldStatus());
        history.setNewStatus(task.getStatus());
        history.setChangeDate(new Date());
        history.setAssignedUser(task.getAssignedUser());

        taskStatusHistoryRepository.save(history);
    }

    @PreUpdate
    public void onCommentPreUpdate(Comment comment) {
        comment.setOldContent(comment.getContent());
    }

    @PostUpdate
    @Transactional
    public void onCommentPostUpdate(Comment comment) {
        CommentHistory history = new CommentHistory();
        history.setComment(comment);
        history.setContent(comment.getOldContent());
        history.setDate(new Date());
        history.setAuthor(comment.getAuthor());
        history.setAssignedUser(comment.getAssignedUser());

        commentHistoryRepository.save(history);
    }
}

