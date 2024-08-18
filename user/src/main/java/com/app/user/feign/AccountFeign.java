package com.app.user.feign;

import java.util.List;

import com.app.user.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountFeign {

  @GetMapping("/accounts/getAll")
  List<Account> getAllAccounts(@RequestHeader("Authorization") String token);

  @GetMapping("/accounts/accountNumber/{accountNumber}")
  Account getAllByAccountNumber(@PathVariable Long accountNumber);

  @GetMapping("/accounts/accountId/{accountId}")
  Account getAllById(@PathVariable String accountId);
}
