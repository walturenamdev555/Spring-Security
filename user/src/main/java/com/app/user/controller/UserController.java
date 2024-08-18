package com.app.user.controller;

import com.app.user.domain.Account;
import com.app.user.domain.User;
import com.app.user.service.TransferServiceClient;
import com.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private TransferServiceClient transferServiceClient;

  @Autowired private UserService userService;

  @PostMapping("/save")
  public User login(@RequestBody User user) {
    return userService.userLogin(user);
  }

  @GetMapping("/get/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ') or principal == #id")
  public User getUser(@PathVariable String id) {
    return userService.findById(id);
  }

  @GetMapping("/getAllAccounts")
  public ResponseEntity<List<Account>> getAccounts(@RequestHeader("Authorization") String token) {
    System.out.println("Token " + token);
    return ResponseEntity.ok().body(transferServiceClient.getAccounts(token));
  }

  @GetMapping("/status")
  public String getStatus(){
    return "User Found Successfully";
  }
}
