package com.fourimpact.module5_taskmanager.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    (nullable = false, length = 100)
    private String name;

    @Column
    (columnDefinition = "NVARCHAR (MAX)")
    private String description;

    // One Category has many Tasks.
    // 'mappedBy = "category"' refers to the 'category' field inside the Task class.
    // You will add that field to Task in Section 5.

    @OneToMany
    (mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    // No arg and parameterized Constructor
    public Category() {}

    public Category(String name, String description) {
        this.name        = name;
        this.description = description;
    }

    // Getters and Setters
    public Long getId()               {
        return id; }

    public String getName()           {
        return name; }

    public void setName(String n)     {
        this.name = n; }

    public String getDescription()    {
        return description; }

    public void setDescription(String d) {
        this.description = d; }

    public List<Task> getTasks()      {
        return tasks; }
}
