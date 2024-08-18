package com.app.user.service;

import com.app.user.domain.User;
import com.app.user.entity.UserEntity;
import com.app.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public User userLogin(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setId(UUID.randomUUID().toString());
    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(user, userEntity);
    userRepository.save(userEntity);
    return user;
  }

  @Override
  public User findById(String id) {
    Optional<UserEntity> byId = userRepository.findById(id);
    return userRepository.findById(id).map(user -> User.builder()
            .id(user.getUserId())
            .email(user.getEmail())
            .build()).orElse(null);
  }

  @Override
  public UserEntity findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println(username);
    UserEntity entity = userRepository.findByEmail(username);
    if (entity == null) {
      throw new UsernameNotFoundException(username);
    }
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    entity
        .getRoles()
        .forEach(
            role -> {
              authorities.add(new SimpleGrantedAuthority(role.getName()));
              role.getAuthorities()
                  .forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth.getName())));
            });
    return new org.springframework.security.core.userdetails.User(
        username, entity.getEncryptedPassword(), true, true, true, true, authorities);
  }
}
