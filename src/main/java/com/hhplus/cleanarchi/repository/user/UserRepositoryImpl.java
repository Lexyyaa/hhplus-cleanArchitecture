package com.hhplus.cleanarchi.repository.user;

import com.hhplus.cleanarchi.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final JPAUserRepository jpaUserRepository;
    @Override
    public User findById(Long userId) {
        return jpaUserRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));
    }
}
