import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProjectsService } from '../../services/projects/projects.service';
import { UserService } from '../../services/user.service'; 
import { Project, Status, SanteGenerale, Avancement, RespectBudget, User } from '../../models/stage.models';

@Component({
  standalone: true,
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css'],
  imports: [CommonModule, FormsModule]
})
export class AddProjectComponent implements OnInit {
  project: Project = {} as Project;
  statusOptions = Object.values(Status); 
  santeGeneraleOptions = Object.values(SanteGenerale);
  avancementOptions = Object.values(Avancement);
  respectBudgetOptions = Object.values(RespectBudget);
  users: User[] = []; 

  constructor(
    private projectsService: ProjectsService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllAccounts().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (err) => console.error('Error loading users:', err)
    });
  }

  createProject() {
    this.projectsService.createProject(this.project).subscribe({
      next: (newProject) => {
        console.log('Project created:', newProject);
        if (this.project.assignedUser) {
          // Ensure `this.project.assignedUser` is a number
          this.projectsService.assignUserToProject(this.project.assignedUser, newProject.id).subscribe({
            next: () => {
              console.log('User assigned to project');
              this.router.navigate(['/']); 
            },
            error: (err) => console.error('Error assigning user:', err)
          });
        } else {
          this.router.navigate(['/']); 
        }
      },
      error: (err) => console.error('Error creating project:', err)
    });
  }
}
