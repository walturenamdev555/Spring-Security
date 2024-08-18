package com.app.account.service;

import com.app.account.domain.Account;

import java.util.List;

public interface AccountService {
    Account addAccount(Account account);

    String addDefaultAccounts();

    List<Account> getAllAccounts();

    Account getByAccountNumber(Long accountNumber);

    Account getById(String id);
}
