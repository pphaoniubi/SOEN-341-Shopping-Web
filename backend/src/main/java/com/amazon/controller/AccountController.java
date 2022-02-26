package com.amazon.controller;

import com.amazon.dto.AccountDto;
import com.amazon.dto.LoginDto;
import com.amazon.entity.Account;
import com.amazon.entity.Role;
import com.amazon.mapper.AccountMapper;
import com.amazon.registration.AccountRole;
import com.amazon.registration.RegistrationRequest;
import com.amazon.registration.RegistrationService;
import com.amazon.repository.AccountRepository;
import com.amazon.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private RegistrationService registrationService;

    public AccountController(AccountService accountService, AccountRepository accountRepository,
                             RegistrationService registrationService) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public AccountDto login(@Valid @RequestBody LoginDto loginDto,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        Account account = null;
        try {
            account = accountService.login(loginDto.getEmail(), loginDto.getPassword(), request);
        } catch (Exception e) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid email or password!");
            return null;
        }
        return AccountMapper.INSTANCE.map(account);
    }

   /* @PostMapping(value = "/register", consumes = "application/json")
    public String register(@Valid @RequestBody Account account, RegistrationRequest request, HttpServletResponse response) throws IOException {
        try {
           registrationService.register(request);
        } catch (Exception e) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid email or password!");
            return null;
        }
        return registrationService.register(request);
    }*/
    @PostMapping(value = "/register", consumes = "application/json")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
