import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project, User, Task, ProjectStatusHistory } from '../../models/stage.models';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ProjectsService {
  private apiUrl = 'http://localhost:8091/admin/api/projects';

  constructor(private http: HttpClient) { }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/GetAllProjects`);
  }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/GetProjectById/${id}`);
  }

  createProject(project: Project): Observable<Project> {
    return this.http.post<Project>(`${this.apiUrl}/AddProject`, project);
  }

  updateProject(id: number, project: Project): Observable<Project> {
    return this.http.put<Project>(`${this.apiUrl}/UpdateProject/${id}`, project);
  }

  deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/DeleteProject/${id}`);
  }

  assignUserToProject(userId: number, projectId: number): Observable<User> {
    const url = `http://localhost:8091/admin/AffectUserToProject/${userId}/assign/${projectId}`;
    return this.http.post<User>(url, {});
  }

  getTasksByProject(projectId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/GetTasksByProject/${projectId}`);
  }

  getStatusHistoryByProject(projectId: number): Observable<ProjectStatusHistory[]> {
    const url = `${this.apiUrl}/byProject/${projectId}`;
    return this.http.get<ProjectStatusHistory[]>(url);
  }

  getProgressPercentage(projectId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/GetProgressPercentage/${projectId}`);
  }
  
}
