package com.example.gameexchanger.api;

import com.example.gameexchanger.dto.AccountInfoDto;
import com.example.gameexchanger.dto.AccountRegisterDto;
import com.example.gameexchanger.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody AccountRegisterDto accountRegisterDto) {
        accountService.registerAccount(accountRegisterDto);
        return ResponseEntity.ok("created");
    }

    @GetMapping("/info/{pesel}")
    public ResponseEntity<AccountInfoDto> getAccountInfo(@PathVariable String pesel) {
        AccountInfoDto accountInfoDto = accountService.getAccountInfo(pesel);
        return ResponseEntity.ok(accountInfoDto);
    }
}
