package com.example.kanban.repository;

import com.example.kanban.model.KanbanUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<KanbanUser, Long> {
    KanbanUser findByUsername(String username);
}