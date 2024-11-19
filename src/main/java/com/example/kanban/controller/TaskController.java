package com.example.kanban.controller;

import com.example.kanban.service.TaskService;
import com.example.kanban.model.KanbanTask;
import com.example.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    public TaskController(TaskService kanbanTaskService) {
        this.taskService = kanbanTaskService;
    }

    @PostMapping
    public ResponseEntity<KanbanTask> createTask(@RequestBody KanbanTask task) {
        KanbanTask createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KanbanTask> updateTask(@PathVariable Long id, @RequestBody KanbanTask updatedTask) {
        KanbanTask task = taskService.updateTask(id, updatedTask);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    // Mover tarefa para a próxima coluna
    @PutMapping("/{id}/move")
    public ResponseEntity<KanbanTask> moveTask(@PathVariable Long id) {
        KanbanTask movedTask = taskService.moveTask(id);
        if (movedTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movedTask);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}