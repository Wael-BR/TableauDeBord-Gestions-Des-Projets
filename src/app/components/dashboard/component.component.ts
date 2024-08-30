import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProjectsService } from '../../services/projects/projects.service';
import { UserService } from '../../services/user.service';
import { Project, User, Task, RespectBudget } from '../../models/stage.models';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { ChartModule } from 'primeng/chart';
import { BadgeModule } from 'primeng/badge';

@Component({
  selector: 'app-component',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, TableModule, ChartModule, BadgeModule,RouterModule],
  templateUrl: './component.component.html',
  styleUrls: ['./component.component.css']
})
export class ComponentComponent implements OnInit {
  project: Project | null = null;
  projectId: number | null = null;
  users: User[] = [];
  tasks: Task[] = [];
  statusData: any; // Data for the pie chart
  chartOptions: any; // Options for the pie chart
  daysPassed: number = 0;
  daysLeft: number = 0;
  daysToComplete: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private projectsService: ProjectsService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.projectId = +params.get('id')!;
      this.loadProject();
    });
  }

  loadProject(): void {
    if (this.projectId !== null) {
      this.projectsService.getProjectById(this.projectId).subscribe((data: Project) => {
        this.project = data;
        this.calculateMetrics();
        this.loadUsers(data.id);
        this.loadTasks(data.id);
        this.prepareChartData();
      });
    }
  }

  loadUsers(projectId: number): void {
    this.userService.getUsersByProject(projectId).subscribe((users: User[]) => {
      this.users = users;
    });
  }

  loadTasks(projectId: number): void {
    this.projectsService.getTasksByProject(projectId).subscribe((tasks: Task[]) => {
      this.tasks = tasks;
      this.prepareChartData(); // Prepare chart data after loading tasks
    });
  }

  calculateMetrics(): void {
    if (this.project) {
      const today = new Date();
      const startDate = new Date(this.project.startDate);
      const endDate = new Date(this.project.endDate);

      this.daysPassed = Math.floor((today.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
      this.daysLeft = Math.floor((endDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
      this.daysToComplete = this.daysLeft;
    }
  }

  getRespectPlanningClass(respectPlanning: string): string {
    switch (respectPlanning) {
      case 'Respecté':
        return 'respect-planning-respected';
      case 'En_retard':
        return 'respect-planning-delayed';
      case 'Plus_ou_moins_respecté':
        return 'respect-planning-partially-respected';
      default:
        return '';
    }
  }

  getRespectBudgetClass(respectBudget: RespectBudget): string {
    switch (respectBudget) {
      case RespectBudget.Respecte:
        return 'respect-budget-respected';
      case RespectBudget.EnSurconsommation:
        return 'respect-budget-overconsumed';
      default:
        return '';
    }
  }

  getSeverity(status: string): 'success' | 'info' | 'warning' | 'danger' | 'help' | 'primary' | 'secondary' | 'contrast' | null {
    switch (status) {
      case 'EN_COURS':
        return 'warning';
      case 'ANNULE_ET_CLOTURE':
        return 'danger';
      case 'LIVRE_ET_CLOTURE':
        return 'success';
      case 'LIVRE':
        return 'info';
      case 'NON_DEMARRE':
        return 'secondary';
      case 'EN_ATTENTE':
        return 'primary';
      default:
        return null;
    }
  }

  calculateBudgetDifference(): number {
    return this.project ? this.project.budget - this.project.actualBudget : 0;
  }

  prepareChartData(): void {
    if (this.tasks.length > 0) {
      const statusCounts = this.tasks.reduce((acc, task) => {
        acc[task.status] = (acc[task.status] || 0) + 1;
        return acc;
      }, {} as { [key: string]: number });

      this.statusData = {
        labels: Object.keys(statusCounts),
        datasets: [{
          data: Object.values(statusCounts),
          backgroundColor: ['#FFC107', '#FF5722', '#4CAF50', '#03A9F4', '#9E9E9E', '#00BCD4']
        }]
      };

      this.chartOptions = {
        responsive: true,
        plugins: {
          legend: {
            position: 'top'
          },
          tooltip: {
            callbacks: {
              label: (context: any) => {
                const label = context.label || '';
                const value = context.raw || 0;
                return `${label}: ${value}`;
              }
            }
          }
        }
      };
    }
  }
}
