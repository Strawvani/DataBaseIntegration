package com.fourimpact.module5_taskmanager.Controller;

import com.fourimpact.module5_taskmanager.DTO.CreateTaskRequest;
import com.fourimpact.module5_taskmanager.DTO.TaskResponse;
import com.fourimpact.module5_taskmanager.Service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")

public class TaskController {

    private final TaskService taskService;

    // No @Autowired needed on a single constructor
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /api/tasks
    // Client sends JSON body matching CreateTaskRequest fields
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    // GET /api/tasks
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // GET /api/tasks/5
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/api/tasks/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTaskByStatus(@PathVariable String status){
        return ResponseEntity.ok((taskService.getTaskByStatus(status)));
    }
    // PUT /api/tasks/5
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    // DELETE /api/tasks/5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/tasks/5/tags/2 -- assign tag 2 to task 5
    @PostMapping("/{taskId}/tags/{tagId}")
    public ResponseEntity<TaskResponse> addTagToTask(@PathVariable Long taskId, @PathVariable Long tagId) {
        return ResponseEntity.ok(taskService.addTagToTask(taskId, tagId));
    }

    // GET /api/tasks/paged?userId=1&page=0&size=5&sortBy=createdAt&direction=desc
    @GetMapping("/paged")
    public ResponseEntity<Page<TaskResponse>> getTasksPaged(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(taskService.getTasksPaged(userId, page, size, sortBy, direction));
    }

}
