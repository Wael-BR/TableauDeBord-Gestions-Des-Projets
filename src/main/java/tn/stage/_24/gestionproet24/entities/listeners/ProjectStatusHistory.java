package tn.stage._24.gestionproet24.entities.listeners;

import jakarta.persistence.*;
import lombok.*;
import tn.stage._24.gestionproet24.entities.Project;
import tn.stage._24.gestionproet24.entities.Status;
import tn.stage._24.gestionproet24.entities.User;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Status oldStatus;

    @Enumerated(EnumType.STRING)
    private Status newStatus;

    private Date changeDate;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
}
