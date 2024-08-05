package tn.stage._24.gestionproet24.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import tn.stage._24.gestionproet24.entities.Status;
import tn.stage._24.gestionproet24.entities.Task;

import java.time.LocalDateTime;

@Getter
public class TaskStatusChangeEvent extends ApplicationEvent {

    private final Task task;
    private final Status oldStatus;
    private final Status newStatus;
    private final LocalDateTime changeDate;

    public TaskStatusChangeEvent(Object source, Task task, Status oldStatus, Status newStatus) {
        super(source);
        this.task = task;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changeDate = LocalDateTime.now(); // Set the change date to the current date and time
    }
}
