package tn.stage._24.gestionproet24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.stage._24.gestionproet24.entities.Comment;
import tn.stage._24.gestionproet24.entities.Project;
import tn.stage._24.gestionproet24.entities.Task;
import tn.stage._24.gestionproet24.services.TaskService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/GetAll")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/GetById/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") int id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/Add")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @PostMapping("/Add2")
    public ResponseEntity<?> createTask2(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask2(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            // Log error details and return appropriate response
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating task");
        }
    }


    @PutMapping("/Update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") int id, @RequestBody Task taskDetails) {
        /*Task task = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(task);*/
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/assignTaskToProject/{taskId}/{projectId}")
    public Task assignToCourse(@PathVariable("taskId") int taskId,
                                  @PathVariable("projectId") int projectId) {
        return taskService.assignTaskToProject(taskId, projectId);
    }
}
