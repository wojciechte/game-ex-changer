package com.example.gameexchanger.service;

import com.example.gameexchanger.dto.AccountInfoDto;
import com.example.gameexchanger.dto.AccountRegisterDto;

public interface IAccountService {

    void registerAccount(AccountRegisterDto accountRegisterDto);

    AccountInfoDto getAccountInfo(String pesel);

}
