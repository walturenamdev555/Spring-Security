package com.app.account.service;

import com.app.account.domain.Account;
import com.app.account.domain.AccountType;
import com.app.account.entity.AccountEntity;
import com.app.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository repository;

  @Override
  public Account addAccount(Account account) {
    System.out.println(account);
    AccountEntity entity = new AccountEntity();
    BeanUtils.copyProperties(account, entity);
    entity.setId(UUID.randomUUID().toString());
    repository.save(entity);
    Account account1 = new Account();
    BeanUtils.copyProperties(repository.save(entity), account1);
    return account1;
  }

  @Override
  public List<Account> getAllAccounts() {
    List<AccountEntity> all = repository.findAll();
    return Optional.ofNullable(all)
        .map(
            result ->
                result.stream()
                    .map(
                        entity ->
                            Account.builder()
                                .id(entity.getId())
                                .accountNumber(entity.getAccountNumber())
                                .accountHolderName(entity.getAccountHolderName())
                                .type(entity.getType())
                                .isActive(entity.isActive())
                                .build())
                    .toList())
        .orElse(List.of());
  }

  @Override
  public Account getByAccountNumber(Long accountNumber) {
    return repository
        .findByAccountNumber(accountNumber)
        .map(
            entity ->
                Account.builder()
                    .id(entity.getId())
                    .accountNumber(entity.getAccountNumber())
                    .accountHolderName(entity.getAccountHolderName())
                    .type(entity.getType())
                    .isActive(entity.isActive())
                    .build())
        .orElse(null);
  }

  @Override
  public String addDefaultAccounts() {
    List<AccountEntity> accountEntities = repository.saveAllAndFlush(getDefaultAccounts());
    return Optional.ofNullable(accountEntities)
        .map(list -> "Accounts saved successfully")
        .orElse("Accounts failed to save");
  }

  @Override
  public Account getById(String id) {
    return repository
        .findById(id)
        .map(
            entity ->
                Account.builder()
                    .id(entity.getId())
                    .accountNumber(entity.getAccountNumber())
                    .accountHolderName(entity.getAccountHolderName())
                    .type(entity.getType())
                    .isActive(entity.isActive())
                    .build())
        .orElse(null);
  }

  private List<AccountEntity> getDefaultAccounts() {
    AccountEntity entity1 = new AccountEntity();
    AccountEntity entity2 = new AccountEntity();
    AccountEntity entity3 = new AccountEntity();
    AccountEntity entity4 = new AccountEntity();
    AccountEntity entity5 = new AccountEntity();

    entity1.setId(UUID.randomUUID().toString());
    entity1.setAccountNumber(11112222L);
    entity1.setAccountHolderName("Namdev Walture");
    entity1.setType(AccountType.SALARY);
    entity1.setActive(true);

    entity2.setId(UUID.randomUUID().toString());
    entity2.setAccountNumber(22223333L);
    entity2.setAccountHolderName("Pawan Gawande");
    entity2.setType(AccountType.SALARY);
    entity2.setActive(true);

    entity3.setId(UUID.randomUUID().toString());
    entity3.setAccountNumber(33334444L);
    entity3.setAccountHolderName("Akshay Netankar");
    entity3.setType(AccountType.PF);
    entity3.setActive(true);

    entity4.setId(UUID.randomUUID().toString());
    entity4.setAccountNumber(44445555L);
    entity4.setAccountHolderName("Vikas Gaikwad");
    entity4.setType(AccountType.SAVING);
    entity4.setActive(true);

    entity5.setId(UUID.randomUUID().toString());
    entity5.setAccountNumber(55556666L);
    entity5.setAccountHolderName("Kishor Hiwale");
    entity5.setType(AccountType.CURRENT);
    entity5.setActive(true);

    List<AccountEntity> list = new ArrayList();
    list.add(entity1);
    list.add(entity2);
    list.add(entity3);
    list.add(entity4);
    list.add(entity5);
    return list;
  }
}
