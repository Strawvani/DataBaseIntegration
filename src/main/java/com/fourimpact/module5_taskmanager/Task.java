package com.fourimpact.module5_taskmanager;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // Many Tasks belong to one User.
    // @JoinColumn creates a 'user_id' foreign key column in the tasks table.
    // fetch = FetchType.LAZY overrides the @ManyToOne default (which is Eager) to Lazy.
    // This means the User will only be loaded from the database when you call getUser().
    // This is best practice -- we use JOIN FETCH in @Query when we need the User loaded.
    // (Lazy vs Eager loading is explained in full in Section 8.1.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many Tasks belong to one Category.
    // Same reasoning as above -- Lazy fetch overrides the Eager default.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // @JoinTable defines the join table that links tasks and tags.
    // 'task_tags' is the join table name, with two foreign key columns.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "task_tags",
            joinColumns        = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    public List<Tag> getTags() { return tags; }

    // Helper method -- always use this to add a tag, never add directly to the list.
    public void addTag(Tag tag) {
        this.tags.add(tag);       // add tag to this task's list
        tag.getTasks().add(this); // add this task to the tag's list (keep both sides in sync)
    }

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

    public User getUser()               { return user; }
    public void setUser(User u)         { this.user = u; }
    public Category getCategory()       { return category; }
    public void setCategory(Category c) { this.category = c; }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', status='" + status + "'}";
    }
}
