package tn.stage._24.gestionproet24.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import tn.stage._24.gestionproet24.entities.Comment;
import tn.stage._24.gestionproet24.entities.User;

import java.time.LocalDateTime;

@Getter
public class CommentChangeEvent extends ApplicationEvent {

    private final Comment comment;
    private final User author;
    private final String content;
    private final LocalDateTime changeDate;

    public CommentChangeEvent(Object source, Comment comment, User author, String content) {
        super(source);
        this.comment = comment;
        this.author = author;
        this.content = content;
        this.changeDate = LocalDateTime.now();
    }
}
