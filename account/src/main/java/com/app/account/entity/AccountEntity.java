package com.app.account.entity;

import com.app.account.domain.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Account")
public class AccountEntity {

  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "accountNumber", nullable = false)
  private Long accountNumber;

  @Column(name = "holderName", nullable = false)
  private String accountHolderName;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private AccountType type;

  @Column(name = "isActive", nullable = false)
  private boolean isActive;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }

  public void setAccountHolderName(String accountHolderName) {
    this.accountHolderName = accountHolderName;
  }

  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
