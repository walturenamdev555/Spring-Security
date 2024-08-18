package com.app.transafer.service;

import com.app.transafer.domain.Account;
import com.app.transafer.feign.AccountFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
  @Autowired
  private AccountFeign accountFeign;

  @Override
  public List<Account> getAccounts() {
    return accountFeign.getAllAccounts();
  }
}
