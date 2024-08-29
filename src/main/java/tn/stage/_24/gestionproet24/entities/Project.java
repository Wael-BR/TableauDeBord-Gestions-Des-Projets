package tn.stage._24.gestionproet24.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private SanteGenerale santeGenerale;
    @Enumerated(EnumType.STRING)
    private Avancement respectPlanning;
    @Enumerated(EnumType.STRING)
    private Avancement respectPerimetre;
    @Enumerated(EnumType.STRING)
    private RespectBudget respectBudget;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private java.util.Date dateLivraison;
    private String priority;
    private String type;
    private float budget;
    private float actualBudget;
    @Transient
    private float progressPercentage;


    @Transient
    private Status oldStatus;
    @Transient
    private SanteGenerale oldSanteGenerale;
    @Transient
    private Avancement oldRespectPlanning;
    @Transient
    private Avancement oldRespectPerimetre;
    @Transient
    private RespectBudget oldRespectBudget;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Task> tasks;

    @ManyToMany(mappedBy = "projects", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> users;
}
