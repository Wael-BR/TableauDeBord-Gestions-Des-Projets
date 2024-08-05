package tn.stage._24.gestionproet24.entitiesListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import tn.stage._24.gestionproet24.entities.listeners.CommentHistory;
import tn.stage._24.gestionproet24.events.CommentChangeEvent;
import tn.stage._24.gestionproet24.repository.listeners.CommentHistoryRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class CommentChangeListener {

    @Autowired
    private CommentHistoryRepository commentHistoryRepository;

    @EventListener
    public void onCommentChange(CommentChangeEvent event) {
        CommentHistory history = new CommentHistory();
        history.setComment(event.getComment());
        history.setAuthor(event.getAuthor());
        history.setContent(event.getContent());

        // Convert LocalDateTime to Date
        LocalDateTime changeDateTime = event.getChangeDate();
        Date changeDate = Date.from(changeDateTime.atZone(ZoneId.systemDefault()).toInstant());
        history.setDate(changeDate);

        // Save the record to the database
        commentHistoryRepository.save(history);
        System.out.println("Event received for comment ID: " + event.getComment().getId());
    }
}
