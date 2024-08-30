import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Project } from '../../models/stage.models';
import { ProjectsService } from '../../services/projects/projects.service';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TagModule } from 'primeng/tag';
import { ProgressBarModule } from 'primeng/progressbar';
import { SliderModule } from 'primeng/slider';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, TableModule, RouterModule, 
    HttpClientModule, ButtonModule, InputTextModule, 
    TagModule, ProgressBarModule, SliderModule,
    FormsModule  
  ],
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {
  projects: Project[] = [];
  filteredProjects: Project[] = [];
  progress: Map<number, number> = new Map<number, number>();
  progressValues: number[] = [0, 100];

  constructor(private projectsService: ProjectsService) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.projectsService.getAllProjects().subscribe((data: Project[]) => {
      this.projects = data;
      this.filteredProjects = data;
      this.projects.forEach(project => {
        this.projectsService.getProgressPercentage(project.id).subscribe(percentage => {
          this.progress.set(project.id, percentage);
        });
      });
    });
  }

  deleteProject(id: number): void {
    this.projectsService.deleteProject(id).subscribe(() => {
      this.projects = this.projects.filter(p => p.id !== id);
      this.filteredProjects = this.filteredProjects.filter(p => p.id !== id);
      this.progress.delete(id);
    });
  }

  filterByProgress(event: any): void {
    const [min, max] = event.values;
    this.filteredProjects = this.projects.filter(project => {
      const progressPercentage = this.progress.get(project.id) || 0;
      return progressPercentage >= min && progressPercentage <= max;
    });
  }

  getStatusSeverity(status: string): string {
    switch (status) {
      case 'EN_COURS':
        return 'info';
      case 'ANNULE_ET_CLOTURE':
        return 'danger';
      case 'LIVRE_ET_CLOTURE':
        return 'success';
      default:
        return 'warning';
    }
  }
}
