package com.zeero.zeero.dto.response;

import com.zeero.zeero.enums.Priority;
import com.zeero.zeero.model.Tasks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private boolean completed;
    private Long userId;

    public TaskResponse(Tasks task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.priority = task.getPriority();
        this.completed = task.isCompleted();
        this.userId = task.getId();
    }
}
