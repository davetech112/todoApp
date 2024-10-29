package com.zeero.zeero.controller;

import com.zeero.zeero.dto.request.TasksRequest;
import com.zeero.zeero.dto.response.TaskResponse;
import com.zeero.zeero.dto.response.GenericResponse;
import com.zeero.zeero.service.TasksService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TasksService tasksService;

    @PostMapping("/create-task")
    public ResponseEntity<GenericResponse<TaskResponse>> createNewTask(@RequestBody TasksRequest request){
        return ResponseEntity.ok(tasksService.createTasks(request));
    }
    @PatchMapping("/complete-task/{id}")
    public ResponseEntity<GenericResponse<TaskResponse>> completeTask(@PathVariable Long id){
        return ResponseEntity.ok(tasksService.completeTask(id));
    }
    @PutMapping("/update-task/{id}")
    public ResponseEntity<GenericResponse<TaskResponse>> updateTask(@PathVariable Long id, @RequestBody TasksRequest request){
        return ResponseEntity.ok(tasksService.updateTask(id,request));
    }
    @GetMapping("/get-task/{id}")
    public ResponseEntity<GenericResponse<TaskResponse>> getTask(@PathVariable("id") Long taskId){
        return ResponseEntity.ok(tasksService.getTask(taskId));
    }
    @GetMapping("/get-all-tasks")
    public ResponseEntity<GenericResponse<List<TaskResponse>>> getAllTasks(Pageable pageable){
        return ResponseEntity.ok(tasksService.getAllTask(pageable));
    }
    @GetMapping("/get-all-user-tasks")
    public ResponseEntity<GenericResponse<List<TaskResponse>>> getAllTaskByUser(@PathVariable Long userId, Pageable pageable){
        return ResponseEntity.ok(tasksService.getAllTaskByAUser(userId, pageable));
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<GenericResponse<String>> deleteTask(@PathVariable Long id){
        return ResponseEntity.ok(tasksService.deleteTask(id));
    }
    
}
