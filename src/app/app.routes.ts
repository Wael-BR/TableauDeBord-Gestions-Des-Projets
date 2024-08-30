import { provideRouter, Routes } from '@angular/router';
import { ProjectsComponent } from './components/projects/projects.component'; 
import { ComponentComponent } from './components/dashboard/component.component';
import { AppLayoutComponent } from './components/app-layout/app-layout.component';
import { AddProjectComponent } from './components/add-project/add-project.component';
import { SelectTasksComponent } from './components/select-tasks/select-tasks.component';
import { EditProjectComponent } from './components/edit-project/edit-project.component';

// Export routes
export const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent,
    children: [
      { path: 'projects', component: ProjectsComponent },
      { path: 'add-project', component: AddProjectComponent },
      { path: 'project-details/:id', component: ProjectsComponent },
      { path: 'dashboard/:id', component: ComponentComponent },
      { path: 'select-tasks/:id', component: SelectTasksComponent },
      { path: 'edit-project/:id', component: EditProjectComponent }, 
      { path: '**', redirectTo: 'projects' }
    ]
  }
];

// Provide router directly
export const AppRoutingModule = provideRouter(routes);
