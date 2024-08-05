package tn.stage._24.gestionproet24.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import tn.stage._24.gestionproet24.entities.listeners.TaskStatusHistory;
import tn.stage._24.gestionproet24.events.TaskStatusChangeEvent;

import tn.stage._24.gestionproet24.repository.listeners.TaskStatusHistoryRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class TaskStatusChangeListener {

    @Autowired
    private TaskStatusHistoryRepository taskStatusHistoryRepository;

    @EventListener
    public void onTaskStatusChange(TaskStatusChangeEvent event) {
        // Create a new TaskStatusHistory record
        TaskStatusHistory history = new TaskStatusHistory();
        history.setTask(event.getTask());
        history.setOldStatus(event.getOldStatus());
        history.setNewStatus(event.getNewStatus());
        history.setChangeDate(Date.from(event.getChangeDate().atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println("Event received for task ID: " + event.getTask().getId());
        taskStatusHistoryRepository.save(history);
    }
}
