package com.fourimpact.module5_taskmanager.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    (name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column
    (name = "email", nullable = false, unique = true, length = 150)
    private String email;

    // One User has many Tasks.
    // cascade = CascadeType.ALL  -- explained in Section 5.3
    // fetch = FetchType.LAZY     -- explained in Section 8.1
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    // No arg and parameterized Constructor
    public User() {}
    public User(String username, String email) {
        this.username = username;
        this.email    = email;
    }

    // Getters and Setters
    public Long getId()               { return id; }
    public String getUsername()       { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getEmail()          { return email; }
    public void setEmail(String e)    { this.email = e; }
    public List<Task> getTasks()      { return tasks; }
}
