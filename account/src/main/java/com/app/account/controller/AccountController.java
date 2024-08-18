package com.app.account.controller;

import com.app.account.domain.Account;
import com.app.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
  @Autowired private AccountService accountService;

  @PostMapping
  public ResponseEntity<Account> addAccount(@RequestBody Account account) {
    return ResponseEntity.status(HttpStatus.OK).body(accountService.addAccount(account));
  }

  @GetMapping("/addAll")
  public ResponseEntity<String> addDefaultAccounts() {
    return ResponseEntity.ok(accountService.addDefaultAccounts());
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<Account>> getAllAccounts() {
    return ResponseEntity.ok().body(accountService.getAllAccounts());
  }

  @GetMapping("/accountNumber/{accountNumber}")
  public ResponseEntity<Account> getByAccountNumber(
      @PathVariable("accountNumber") Long accountNumber) {
    return ResponseEntity.ok().body(accountService.getByAccountNumber(accountNumber));
  }

  @GetMapping("/accountId/{accountId}")
  public ResponseEntity<Account> getByAccountId(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok().body(accountService.getById(accountId));
  }
}
