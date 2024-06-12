package com.example.gameexchanger.service;

import com.example.gameexchanger.converter.AccountConverter;
import com.example.gameexchanger.dto.AccountInfoDto;
import com.example.gameexchanger.dto.AccountRegisterDto;
import com.example.gameexchanger.exceptions.AccountAlreadyExistsException;
import com.example.gameexchanger.exceptions.AccountNotFoundException;
import com.example.gameexchanger.exceptions.UserIsBelowRequiredAgeException;
import com.example.gameexchanger.model.Account;
import com.example.gameexchanger.repository.AccountRepository;
import com.example.gameexchanger.util.PeselUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Override
    public void registerAccount(AccountRegisterDto accountRegisterDto) {
        verifyAccount(accountRegisterDto.getPesel());

        Account newAccount = AccountConverter.from(accountRegisterDto);

        accountRepository.saveAccount(newAccount);
    }

    @Override
    public AccountInfoDto getAccountInfo(String pesel) {
        PeselUtils.assertIsPeselValid(pesel);
        Account account = accountRepository.findByPesel(pesel);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        return AccountConverter.from(account);
    }

    private void verifyAccount(String pesel) {
        PeselUtils.assertIsPeselValid(pesel);
        verifyUniquenessOfTheAccount(pesel);
        verifyUsersAge(pesel);
    }

    private void verifyUniquenessOfTheAccount(String pesel) {
        Account account = accountRepository.findByPesel(pesel);
        if (account != null) {
            throw new AccountAlreadyExistsException();
        }
    }

    private void verifyUsersAge(String pesel) {
        LocalDate birthDate = PeselUtils.decodeBirthDate(pesel);
        LocalDate currentDate = LocalDate.now();
        int years = Period.between(birthDate, currentDate).getYears();
        if (years < 18) {
            throw new UserIsBelowRequiredAgeException();
        }
    }

}
