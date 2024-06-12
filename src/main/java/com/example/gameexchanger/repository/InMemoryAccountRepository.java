package com.example.gameexchanger.repository;

import com.example.gameexchanger.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> accountUserMap = new ConcurrentHashMap<>();

    @Override
    public Account findByPesel(String pesel) {
        return accountUserMap.get(pesel);
    }

    @Override
    public void saveAccount(Account account) {
        accountUserMap.put(account.getPesel(), account);
    }
}
