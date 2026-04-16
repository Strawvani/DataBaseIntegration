package com.fourimpact.module5_taskmanager.Service;


import com.fourimpact.module5_taskmanager.DTO.CreateTaskRequest;
import com.fourimpact.module5_taskmanager.DTO.TaskResponse;
import com.fourimpact.module5_taskmanager.Entity.Category;
import com.fourimpact.module5_taskmanager.Entity.Tag;
import com.fourimpact.module5_taskmanager.Entity.Task;
import com.fourimpact.module5_taskmanager.Entity.User;
import com.fourimpact.module5_taskmanager.Exception.ResourceNotFoundException;
import com.fourimpact.module5_taskmanager.Repository.CategoryRepository;
import com.fourimpact.module5_taskmanager.Repository.TagRepository;
import com.fourimpact.module5_taskmanager.Repository.TaskRepository;
import com.fourimpact.module5_taskmanager.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // all methods are transactional by default; rolls back on any RuntimeException
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    // No @Autowired needed on a single constructor -- Spring injects automatically
    // Dependency Injection
    public TaskService (TaskRepository taskRepository, UserRepository userRepository, CategoryRepository categoryRepository, TagRepository tagRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    // ── CREATE ─────────────────────────────────────────────────────────────
    public TaskResponse createTask(CreateTaskRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

        // categoryId is optional (the column is nullable) -- only look it up if provided
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", request.getCategoryId()));
        }

        Task task = new Task(request.getTitle(), request.getDescription(), request.getStatus(), request.getPriority());
        task.setUser(user);
        task.setCategory(category);
        return toResponse(taskRepository.save(task));
    }

    // ── READ ───────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)  // optimized for reads -- Hibernate skips dirty checking
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAllWithUserAndCategory()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        return toResponse(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTaskByStatus(String status){
        return  taskRepository.findByStatus(status)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── UPDATE ─────────────────────────────────────────────────────────────
    public TaskResponse updateTask(Long id, CreateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        // Note: userId and categoryId are not updated here for simplicity.
        // In production, you would validate and reassign those fields too.
        return toResponse(taskRepository.save(task));
    }

    // ── DELETE ─────────────────────────────────────────────────────────────
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task", id);
        }
        taskRepository.deleteById(id);
    }

    // ── ASSIGN TAG ─────────────────────────────────────────────────────────
    public TaskResponse addTagToTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", taskId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", tagId));
        task.addTag(tag);  // updates both sides of the ManyToMany relationship
        return toResponse(taskRepository.save(task));
    }

    // ── Helper: convert Task entity to TaskResponse DTO ───────────────────
    public TaskResponse toResponse(Task task) {
        String username     = task.getUser()     != null ? task.getUser().getUsername()  : null;
        String categoryName = task.getCategory() != null ? task.getCategory().getName()  : null;
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                username, categoryName,
                task.getCreatedAt());
    }

}
