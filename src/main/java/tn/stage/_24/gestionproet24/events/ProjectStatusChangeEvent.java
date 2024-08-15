package tn.stage._24.gestionproet24.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import tn.stage._24.gestionproet24.entities.Project;
import tn.stage._24.gestionproet24.entities.Status;
import tn.stage._24.gestionproet24.entities.RespectBudget;
import tn.stage._24.gestionproet24.entities.Avancement;
import tn.stage._24.gestionproet24.entities.SanteGenerale;

import java.time.LocalDateTime;

@Getter
public class ProjectStatusChangeEvent extends ApplicationEvent {

    private final Project project;
    private final Status oldStatus;
    private final Status newStatus;
    private final RespectBudget oldRespectBudget;
    private final RespectBudget newRespectBudget;
    private final Avancement oldRespectPlanning;
    private final Avancement newRespectPlanning;
    private final Avancement oldRespectPerimetre;
    private final Avancement newRespectPerimetre;
    private final SanteGenerale oldSanteGenerale;
    private final SanteGenerale newSanteGenerale;
    private final LocalDateTime changeDate;

    public ProjectStatusChangeEvent(
            Object source,
            Project project,
            Status oldStatus,
            Status newStatus,
            RespectBudget oldRespectBudget,
            RespectBudget newRespectBudget,
            Avancement oldRespectPlanning,
            Avancement newRespectPlanning,
            Avancement oldRespectPerimetre,
            Avancement newRespectPerimetre,
            SanteGenerale oldSanteGenerale,
            SanteGenerale newSanteGenerale
    ) {
        super(source);
        this.project = project;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.oldRespectBudget = oldRespectBudget;
        this.newRespectBudget = newRespectBudget;
        this.oldRespectPlanning = oldRespectPlanning;
        this.newRespectPlanning = newRespectPlanning;
        this.oldRespectPerimetre = oldRespectPerimetre;
        this.newRespectPerimetre = newRespectPerimetre;
        this.oldSanteGenerale = oldSanteGenerale;
        this.newSanteGenerale = newSanteGenerale;
        this.changeDate = LocalDateTime.now();
    }
}
