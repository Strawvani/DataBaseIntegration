package com.fourimpact.module5_taskmanager.DTO;

public class UserResponse {

    // Fields

    private Long id;
    private String username;
    private String email;

    public UserResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters
    public Long getId() {return id;}

    public String getUsername() {return username;}

    public String getEmail() {return email;}

}
