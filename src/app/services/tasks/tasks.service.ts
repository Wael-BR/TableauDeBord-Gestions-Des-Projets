// src/app/services/tasks/task.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task, User } from '../../models/stage.models';


@Injectable({
  providedIn: 'root'
})
export class TasksService {
  private baseUrl = 'http://localhost:8091/admin/api/tasks';

  constructor(private http: HttpClient) { }

  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}/GetAll`);
  }

  getTaskById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.baseUrl}/GetById/${id}`);
  }

  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(`${this.baseUrl}/Add2`, task);
  }

  updateTask(id: number, task: Task): Observable<Task> { 
    return this.http.put<Task>(`${this.baseUrl}/Update/${id}`, task);
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/Delete/${id}`);
  }

  assignUserToTask(userId: number, taskId: number): Observable<User> {
    // Use backticks for interpolation
    const url = `http://localhost:8091/admin/AffectUserToTasks/${userId}/assign/${taskId}`;
    return this.http.post<User>(url, {});
  }

  assignTaskToProject(taskId: number, projectId: number): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/assignTaskToProject/${taskId}/${projectId}`, {});
  }
  
}
