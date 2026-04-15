package com.fourimpact.module5_taskmanager.dto;

// ── CreateTaskRequest.java -- what the client sends to CREATE or UPDATE a task
public class CreateTaskRequest {

        // Fields
        private Long   userId;
        private Long   categoryId;
        private String title;
        private String description;
        private String status;    // "TODO", "IN_PROGRESS", "DONE"
        private String priority;  // "LOW", "MEDIUM", "HIGH"

        // Getters and Setters
        public Long   getUserId()             { return userId; }
        public void   setUserId(Long id)      { this.userId = id; }
        public Long   getCategoryId()         { return categoryId; }
        public void   setCategoryId(Long id)  { this.categoryId = id; }
        public String getTitle()              { return title; }
        public void   setTitle(String t)      { this.title = t; }
        public String getDescription()        { return description; }
        public void   setDescription(String d){ this.description = d; }
        public String getStatus()             { return status; }
        public void   setStatus(String s)     { this.status = s; }
        public String getPriority()           { return priority; }
        public void   setPriority(String p)   { this.priority = p; }
    }

