import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User } from '../models/stage.models';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8091/admin/';

  constructor(private http: HttpClient) { }

  getAllAccounts(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}GetAllAccounts`);
  }

  allowAccountToWork(id: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}allowAccountToWork/${id}`, {});
  }

  blockAccount(id: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}BlockUserToNavigate/${id}`, {});
  }

  getAdminUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}admins`);
  }

  getNonAdminUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}GetNonAdmins`);
  }

  getUsersByProject(projectId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}project/${projectId}`);
  }
  
}

