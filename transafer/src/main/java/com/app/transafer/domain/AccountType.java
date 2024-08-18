package com.app.transafer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountType {
  SAVING("SAVING"),
  CURRENT("CURRENT"),
  SALARY("SALARY"),
  RETIREMENT("RETIREMENT"),
  PF("PF"),
  TRUST("TRUST");
  private String type;

  AccountType(String type) {
    this.type = type;
  }

  @JsonValue
  public String getType() {
    return type;
  }

  @JsonCreator
  public static AccountType fromValue(String value) {
    for (AccountType accountType : AccountType.values()) {
      if (accountType.type.equalsIgnoreCase(value)) {
        return accountType;
      }
    }
    throw new IllegalArgumentException("Unknown enum type " + value);
  }
}
