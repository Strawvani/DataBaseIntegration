package com.fourimpact.module5_taskmanager;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    (name = "title", nullable = false, length = 200)
    private String title;

    @Column
    (name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    // Possible values: "TODO", "IN_PROGRESS", "DONE"
    @Column
    (name = "status", nullable = false, length = 50)
    private String status;

    // Possible values: "LOW", "MEDIUM", "HIGH"
    @Column
    (name = "priority", length = 20)
    private String priority;

    // @CreationTimestamp is a Hibernate annotation that automatically sets
    // this field to the current timestamp when the row is first saved.
    @CreationTimestamp
    @Column
    (name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // @Transient fields are NEVER saved to the database.
    // Like a sticky note on a document -- exists in memory but not filed away.
    @Transient
    private String displayLabel;

    // Getters & Setters
    public Long getId()                  { return id; }
    public String getTitle()             { return title; }
    public void setTitle(String t)       { this.title = t; }
    public String getDescription()       { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getStatus()            { return status; }
    public void setStatus(String s)      { this.status = s; }
    public String getPriority()          { return priority; }
    public void setPriority(String p)    { this.priority = p; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', status='" + status + "'}";
    }
}
