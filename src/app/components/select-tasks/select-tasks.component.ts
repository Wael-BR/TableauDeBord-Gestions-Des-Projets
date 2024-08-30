import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { TasksService } from '../../services/tasks/tasks.service';
import { Task } from '../../models/stage.models';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { HttpClientModule } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-select-tasks',
  standalone: true,
  imports: [CommonModule, TableModule, RouterModule, HttpClientModule, ButtonModule],
  templateUrl: './select-tasks.component.html',
  styleUrls: ['./select-tasks.component.css']
})
export class SelectTasksComponent implements OnInit {
  projectId!: number ;
  tasks: Task[] = [];

  constructor(
    private route: ActivatedRoute,
    private tasksService: TasksService
  ) {}

  ngOnInit(): void {
    this.projectId = +this.route.snapshot.paramMap.get('id')!;
    this.loadTasks();
  }

  loadTasks() {
    this.tasksService.getAllTasks().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: (err) => console.error('Error loading tasks:', err)
    });
  }

  assignTaskToProject(taskId: number) {
    this.tasksService.assignTaskToProject(this.projectId, taskId).subscribe({
      next: () => {
        console.log('Task assigned successfully!');
      },
      error: (err) => console.error('Error assigning task:', err)
    });
  }
}
