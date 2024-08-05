package tn.stage._24.gestionproet24.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import tn.stage._24.gestionproet24.entities.Project;
import tn.stage._24.gestionproet24.entities.Status;

import java.time.LocalDateTime;

@Getter
public class ProjectStatusChangeEvent extends ApplicationEvent {

    private final Project project;
    private final Status oldStatus;
    private final Status newStatus;
    private final LocalDateTime changeDate;

    public ProjectStatusChangeEvent(Object source, Project project, Status oldStatus, Status newStatus) {
        super(source);
        this.project = project;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changeDate = LocalDateTime.now();
    }
}
