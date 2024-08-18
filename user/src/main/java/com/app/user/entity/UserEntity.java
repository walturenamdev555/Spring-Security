package com.app.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Collection;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

  private static final long serialVersionUID = -2731425678149216053L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 120, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String userId;

  @Column(nullable = false, unique = false)
  private String encryptedPassword;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
  Collection<RoleEntity> roles;

  public void setId(Long id) {
    this.id = id;
  }

  public void setRoles(Collection<RoleEntity> roles) {
    this.roles = roles;
  }

  public Collection<RoleEntity> getRoles() {
    return roles;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getUserId() {
    return userId;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }
  // Getters and setters for all fields
}
