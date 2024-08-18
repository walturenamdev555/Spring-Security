package com.app.user.service;

import com.app.user.domain.User;
import com.app.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User userLogin(User user);

  User findById(String id);

  UserEntity findByEmail(String email);
}
