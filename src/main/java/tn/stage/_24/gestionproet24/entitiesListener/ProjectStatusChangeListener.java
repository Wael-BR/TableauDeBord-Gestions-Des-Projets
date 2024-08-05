package tn.stage._24.gestionproet24.entitiesListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tn.stage._24.gestionproet24.entities.listeners.ProjectStatusHistory;
import tn.stage._24.gestionproet24.events.ProjectStatusChangeEvent;
import tn.stage._24.gestionproet24.repository.listeners.ProjectStatusHistoryRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ProjectStatusChangeListener {

    @Autowired
    private ProjectStatusHistoryRepository projectStatusHistoryRepository;

    @EventListener
    public void onProjectStatusChange(ProjectStatusChangeEvent event) {
        ProjectStatusHistory history = new ProjectStatusHistory();
        history.setProject(event.getProject());
        history.setOldStatus(event.getOldStatus());
        history.setNewStatus(event.getNewStatus());

        // Convert LocalDateTime to Date
        LocalDateTime changeDateTime = event.getChangeDate();
        Date changeDate = Date.from(changeDateTime.atZone(ZoneId.systemDefault()).toInstant());
        history.setChangeDate(changeDate);
        System.out.println("Event received for project ID: " + event.getProject().getId());

        // Save the record to the database
        projectStatusHistoryRepository.save(history);
    }
}
