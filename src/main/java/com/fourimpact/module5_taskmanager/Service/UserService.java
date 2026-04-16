package com.fourimpact.module5_taskmanager.Service;

import com.fourimpact.module5_taskmanager.DTO.UserResponse;
import com.fourimpact.module5_taskmanager.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getUsersPaged(int page, int size, String sortBy){
        Sort sort = Sort.by((sortBy)).ascending();
        return userRepository.findAllUserPaginated(PageRequest.of(page,size,sort))
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()));
    }
}
