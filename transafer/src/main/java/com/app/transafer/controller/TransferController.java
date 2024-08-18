package com.app.transafer.controller;

import com.app.transafer.domain.Account;
import com.app.transafer.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<String> transfer(){
        return ResponseEntity.ok().body("Transfer Successfully");
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>>  getAccounts(){
        return ResponseEntity.ok().body(transferService.getAccounts());
    }

}
