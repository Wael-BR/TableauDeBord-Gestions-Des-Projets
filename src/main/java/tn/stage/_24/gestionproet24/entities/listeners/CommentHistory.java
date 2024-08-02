package tn.stage._24.gestionproet24.entities.listeners;

import jakarta.persistence.*;
import lombok.*;
import tn.stage._24.gestionproet24.entities.Comment;
import tn.stage._24.gestionproet24.entities.User;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String content;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser; // The user assigned to the comment

    // Other fields, constructors, getters, and setters
}
