package com.example.gameexchanger.repository;

import com.example.gameexchanger.model.Account;

public interface AccountRepository {

    Account findByPesel(String pesel);
    void saveAccount(Account account);
}
