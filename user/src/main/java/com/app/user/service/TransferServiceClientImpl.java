package com.app.user.service;

import com.app.user.domain.Account;
import com.app.user.feign.AccountFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceClientImpl implements TransferServiceClient {
  @Autowired private AccountFeign accountFeign;

  @Override
  public List<Account> getAccounts(String token) {
    return accountFeign.getAllAccounts(token);
  }
}
