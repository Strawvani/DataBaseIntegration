package com.fourimpact.module5_taskmanager.Controller;

import com.fourimpact.module5_taskmanager.DTO.UserResponse;
import com.fourimpact.module5_taskmanager.Service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController (UserService userService){
        this.userService = userService;
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<UserResponse>> getUsersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortBy){

        return ResponseEntity.ok((userService.getUsersPaged(page, size, sortBy)));
    }
}
