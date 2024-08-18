package com.app.transafer.feign;

import com.app.transafer.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountFeign {

  @GetMapping("/accounts/getAll")
  List<Account> getAllAccounts();

  @GetMapping("/accounts/accountNumber/{accountNumber}")
  Account getAllByAccountNumber(@PathVariable Long accountNumber);

  @GetMapping("/accounts/accountId/{accountId}")
  Account getAllById(@PathVariable String accountId);
}
