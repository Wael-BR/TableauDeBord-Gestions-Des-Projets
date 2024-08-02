package tn.stage._24.gestionproet24.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private java.util.Date date;
    private String content;
    private String authorType;
    private String commentableType;

    @Transient
    private String oldContent;
    @ManyToOne
    @JoinColumn(name = "assigned_user_id") // Add this field if needed
    private User assignedUser;



    @JsonIgnore
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User author;



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
