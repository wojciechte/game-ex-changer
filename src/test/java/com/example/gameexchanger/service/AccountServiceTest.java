package com.example.gameexchanger.service;

import com.example.gameexchanger.dto.AccountInfoDto;
import com.example.gameexchanger.dto.AccountRegisterDto;
import com.example.gameexchanger.exceptions.AccountAlreadyExistsException;
import com.example.gameexchanger.exceptions.InvalidPeselNumberException;
import com.example.gameexchanger.exceptions.UserIsBelowRequiredAgeException;
import com.example.gameexchanger.fixtures.AccountFixtures;
import com.example.gameexchanger.repository.InMemoryAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest implements AccountFixtures {

    InMemoryAccountRepository inMemoryAccountRepository = new InMemoryAccountRepository();

    AccountService service = new AccountService(inMemoryAccountRepository);

    @Test
    void shouldSuccessfullyRegisterAccount() {
        //given
        AccountRegisterDto accountRegisterDto = getAccountRegisterDto();

        //when
        service.registerAccount(accountRegisterDto);

        //then
        AccountInfoDto registeredAccountInfo = service.getAccountInfo(accountRegisterDto.getPesel());
        assertNotNull(registeredAccountInfo);
    }

    @Test
    void shouldThrowExceptionWhenAccountAlreadyRegistered() {
        //given
        AccountRegisterDto accountRegisterDto = getAccountRegisterDto();

        //when
        service.registerAccount(accountRegisterDto);

        //then
        assertThrows(AccountAlreadyExistsException.class, () -> service.registerAccount(accountRegisterDto));
    }

    @Test
    void shouldThrowExceptionWhenPeselIncorrect() {
        //when & then
        assertThrows(InvalidPeselNumberException.class, () -> service.registerAccount(getAccountRegisterDto("1234567890")));
        assertThrows(InvalidPeselNumberException.class, () -> service.registerAccount(getAccountRegisterDto("555555555555")));
        assertThrows(InvalidPeselNumberException.class, () -> service.registerAccount(getAccountRegisterDto("")));
        assertThrows(InvalidPeselNumberException.class, () -> service.registerAccount(getAccountRegisterDto("0626106523a")));
        assertThrows(InvalidPeselNumberException.class, () -> service.registerAccount(getAccountRegisterDto(null)));
        assertThrows(InvalidPeselNumberException.class, () -> service.registerAccount(getAccountRegisterDto("99999965235")));
    }


    @Test
    void shouldThrowExceptionWhenUserIsUnderAge() {
        //given
        String underAgePesel = "10261077459"; // 2010-06-10
        AccountRegisterDto accountRegisterDto = getAccountRegisterDto(underAgePesel);

        //then
        assertThrows(UserIsBelowRequiredAgeException.class, () -> service.registerAccount(accountRegisterDto));
    }
}