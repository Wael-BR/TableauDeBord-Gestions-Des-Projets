import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { Avancement, Project, RespectBudget, SanteGenerale, Status } from '../../models/stage.models';
import { ProjectsService } from '../../services/projects/projects.service';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-edit-project',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    ButtonModule, 
    InputTextModule, 
    CalendarModule, 
    DropdownModule,
    CardModule
  ],
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {
  public projectId!: number;
  public project: Project = {
    id: 0,
    nom: '',
    description: '',
    startDate: new Date(),
    endDate: new Date(),
    status: Status.NON_DEMARRE,
    santeGenerale: SanteGenerale.SeDerouleCommePrevu,
    respectPlanning: Avancement.Respecte,
    respectPerimetre: Avancement.Respecte,
    respectBudget: RespectBudget.Respecte,
    dateLivraison: new Date(),
    priority: 0,
    type: '',
    budget: 0,
    actualBudget: 0,
    assignedUser: undefined,
    tasks: [],
    users: []
  };
  statusOptions = [
    { label: 'EN_COURS', value: Status.EN_COURS },
    { label: 'ANNULE_ET_CLOTURE', value: Status.ANNULE_ET_CLOTURE },
    { label: 'LIVRE_ET_CLOTURE', value: Status.LIVRE_ET_CLOTURE },
    { label: 'LIVRE', value: Status.LIVRE },
    { label: 'NON_DEMARRE', value: Status.NON_DEMARRE },
    { label: 'EN_ATTENTE', value: Status.EN_ATTENTE }
  ];
  
  constructor(
    private route: ActivatedRoute,
    private projectsService: ProjectsService,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.projectId = +this.route.snapshot.paramMap.get('id')!;
    this.loadProject();
  }

  loadProject(): void {
    this.projectsService.getProjectById(this.projectId).subscribe({
      next: (project) => {
        this.project = project;
      },
      error: (err) => console.error('Error loading project:', err)
    });
  }

  saveProject(): void {
    if (this.project) {
      this.projectsService.updateProject(this.projectId, this.project).subscribe({
        next: () => {
          console.log('Project updated successfully!');
          this.router.navigate(['/projects']);
        },
        error: (err) => console.error('Error updating project:', err)
      });
    }
  }
}
