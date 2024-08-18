package com.app.user.service;

import com.app.user.domain.Account;


import java.util.List;

public interface TransferServiceClient {
    List<Account> getAccounts(String token);
}
