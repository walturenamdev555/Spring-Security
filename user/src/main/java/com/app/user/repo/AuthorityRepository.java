package com.app.user.repo;

import com.app.user.entity.AuthorityEntity;
import com.app.user.entity.RoleEntity;
import com.app.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
  AuthorityEntity findByName(String name);
}
