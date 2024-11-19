package com.example.kanban.service;

import com.example.kanban.model.KanbanTask;
import com.example.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public KanbanTask createTask(KanbanTask task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public List<KanbanTask> getAllTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public KanbanTask updateTask(Long id, KanbanTask updatedTask) {
        Optional<KanbanTask> existingTaskOpt = taskRepository.findById(id); // Chama a instância do repositório
        
        if (existingTaskOpt.isPresent()) {
            KanbanTask existingTask = existingTaskOpt.get();
            
            // Atualiza os campos da tarefa
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setPriority(updatedTask.getPriority());

            // Salva a tarefa atualizada no banco
            return taskRepository.save(existingTask);
        }
        
        return null; // Retorna null se a tarefa não for encontrada
    }

    // Mover uma tarefa para a próxima coluna (A Fazer -> Em Progresso -> Concluído)
    public KanbanTask moveTask(Long id) {
        Optional<KanbanTask> existingTaskOpt = taskRepository.findById(id); // Chama a instância do repositório
        
        if (existingTaskOpt.isPresent()) {
            KanbanTask existingTask = existingTaskOpt.get();

            // Verifica em qual coluna a tarefa está e move para a próxima
            switch (existingTask.getColumnName()) {
                case "A Fazer":
                    existingTask.setColumnName("Em Progresso");
                    break;
                case "Em Progresso":
                    existingTask.setColumnName("Concluído");
                    break;
                case "Concluído":
                    // Tarefa já está na última coluna
                    return null; // Se estiver já concluída, não move
                default:
                    return null; // Caso o nome da coluna seja inválido
            }

            // Salva a tarefa com a nova coluna
            return taskRepository.save(existingTask);
        }
        
        return null; // Retorna null se a tarefa não for encontrada
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}