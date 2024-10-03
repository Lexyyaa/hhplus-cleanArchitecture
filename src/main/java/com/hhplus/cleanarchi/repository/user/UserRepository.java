package com.hhplus.cleanarchi.repository.user;


import com.hhplus.cleanarchi.entity.user.User;

public interface UserRepository {
    User findById(Long userId);
}
