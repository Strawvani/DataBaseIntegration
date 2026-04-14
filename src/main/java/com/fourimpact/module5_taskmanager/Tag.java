package com.fourimpact.module5_taskmanager;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    // This is the inverse side of the ManyToMany relationship.
    // mappedBy = "tags" refers to the 'tags' field in the Task class (defined below).
    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks = new ArrayList<>();

    // No arg and parameterized Constructor
    public Tag() {}
    public Tag(String name){
        this.name = name; }

    // Getters
    public Long getId(){
        return id; }

    public String getName(){
        return name; }

    public List<Task> getTasks(){
        return tasks; }

}
