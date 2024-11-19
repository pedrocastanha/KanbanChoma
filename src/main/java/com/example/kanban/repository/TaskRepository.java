package com.example.kanban.repository;

import com.example.kanban.model.KanbanTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<KanbanTask, Long> {
    List<KanbanTask> findByUserId(Long userId);
}