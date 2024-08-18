package com.app.transafer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
  private String id;
  private Long accountNumber;
  private String accountHolderName;
  private AccountType type;
  private boolean isActive;
}
