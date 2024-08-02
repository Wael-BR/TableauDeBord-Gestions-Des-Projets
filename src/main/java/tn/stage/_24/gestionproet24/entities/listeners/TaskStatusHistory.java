package tn.stage._24.gestionproet24.entities.listeners;

import jakarta.persistence.*;
import lombok.*;
import tn.stage._24.gestionproet24.entities.Status;
import tn.stage._24.gestionproet24.entities.Task;
import tn.stage._24.gestionproet24.entities.User;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    private Status oldStatus;

    @Enumerated(EnumType.STRING)
    private Status newStatus;

    private Date changeDate;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser; // The user assigned to the task

    // Other fields, constructors, getters, and setters
}
