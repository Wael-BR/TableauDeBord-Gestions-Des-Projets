/*
package tn.stage._24.gestionproet24.entitiesListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.annotation.Transactional;
import tn.stage._24.gestionproet24.entities.*;
import tn.stage._24.gestionproet24.repository.listeners.CommentHistoryRepository;
import tn.stage._24.gestionproet24.repository.listeners.ProjectStatusHistoryRepository;
import tn.stage._24.gestionproet24.repository.listeners.TaskStatusHistoryRepository;

import java.util.Date;

import static org.mockito.Mockito.*;

class EntityChangeListenerTest {

    private EntityChangeListener entityChangeListener;

    private ProjectStatusHistoryRepository projectStatusHistoryRepository;
    private TaskStatusHistoryRepository taskStatusHistoryRepository;
    private CommentHistoryRepository commentHistoryRepository;

    @BeforeEach
    void setUp() {
        // Create mock repositories
        projectStatusHistoryRepository = mock(ProjectStatusHistoryRepository.class);
        taskStatusHistoryRepository = mock(TaskStatusHistoryRepository.class);
        commentHistoryRepository = mock(CommentHistoryRepository.class);

        // Initialize the EntityChangeListener with mocks
        entityChangeListener = new EntityChangeListener(
                projectStatusHistoryRepository,
                taskStatusHistoryRepository,
                commentHistoryRepository
        );
    }

    @Test
    @Transactional
    void testOnProjectPreUpdate() {
        Project project = new Project();
        project.setStatus(Status.EN_COURS);

        entityChangeListener.onProjectPreUpdate(project);

        // Validate that the old status was set
        verify(project).setOldStatus(Status.EN_COURS);
    }

    @Test
    @Transactional
    void testOnProjectPostUpdate() {
        Project project = new Project();
        project.setStatus(Status.LIVRE);
        project.setOldStatus(Status.EN_COURS);

        entityChangeListener.onProjectPostUpdate(project);

        // Validate that the ProjectStatusHistory was saved
        verify(projectStatusHistoryRepository).save(any(ProjectStatusHistory.class));
    }

    @Test
    @Transactional
    void testOnTaskPreUpdate() {
        Task task = new Task();
        task.setStatus(Status.EN_COURS);

        entityChangeListener.onTaskPreUpdate(task);

        // Validate that the old status was set
        verify(task).setOldStatus(Status.EN_COURS);
    }

    @Test
    @Transactional
    void testOnTaskPostUpdate() {
        Task task = new Task();
        task.setStatus(Status.LIVRE);
        task.setOldStatus(Status.EN_COURS);

        entityChangeListener.onTaskPostUpdate(task);

        // Validate that the TaskStatusHistory was saved
        verify(taskStatusHistoryRepository).save(any(TaskStatusHistory.class));
    }

    @Test
    @Transactional
    void testOnCommentPreUpdate() {
        Comment comment = new Comment();
        comment.setContent("Initial Content");

        entityChangeListener.onCommentPreUpdate(comment);

        // Validate that the old content was set
        verify(comment).setOldContent("Initial Content");
    }

    @Test
    @Transactional
    void testOnCommentPostUpdate() {
        Comment comment = new Comment();
        comment.setContent("Updated Content");
        comment.setOldContent("Initial Content");

        entityChangeListener.onCommentPostUpdate(comment);

        // Validate that the CommentHistory was saved
        verify(commentHistoryRepository).save(any(CommentHistory.class));
    }
}
*/
