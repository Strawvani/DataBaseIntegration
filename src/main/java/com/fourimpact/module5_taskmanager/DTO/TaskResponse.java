package com.fourimpact.module5_taskmanager.DTO;

import java.time.LocalDateTime;

// ── TaskResponse.java -- what the API returns to the client ───────────────
public class TaskResponse {

    // Fields
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String assignedUsername;  // from User entity
    private String categoryName;      // from Category entity
    private LocalDateTime createdAt;

    // IMPORTANT: Keep argument order consistent with how you call this constructor.
    // Several fields are the same type (String), so swapping arguments will not
    // cause a compile error but WILL produce wrong data in your API response.
    public TaskResponse(Long id, String title, String description, String status, String priority, String assignedUsername, String categoryName, LocalDateTime createdAt) {
        this.id               = id;
        this.title            = title;
        this.description      = description;
        this.status           = status;
        this.priority         = priority;
        this.assignedUsername = assignedUsername;
        this.categoryName     = categoryName;
        this.createdAt        = createdAt;
    }

    // Getters
    public Long          getId()               { return id; }
    public String        getTitle()            { return title; }
    public String        getDescription()      { return description; }
    public String        getStatus()           { return status; }
    public String        getPriority()         { return priority; }
    public String        getAssignedUsername() { return assignedUsername; }
    public String        getCategoryName()     { return categoryName; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
}
