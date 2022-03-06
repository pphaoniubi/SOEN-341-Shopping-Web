package com.amazon.controller;

import com.amazon.dto.AccountDto;
import com.amazon.dto.LoginDto;
import com.amazon.entity.Account;
import com.amazon.mapper.AccountMapper;
import com.amazon.dto.RegisterDto;
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

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public AccountDto login(@Valid @RequestBody LoginDto loginDto,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        Account account;
        try {
            account = accountService.login(loginDto.getEmail(), loginDto.getPassword(), request);
        } catch (Exception e) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid email or password!");
            return null;
        }
        return AccountMapper.INSTANCE.map(account);
    }

    @PostMapping("/register")
    public AccountDto register(@RequestBody RegisterDto request,
                               HttpServletResponse response) throws IOException {
        Account account;
        try {
            account = accountService.register(request);
        } catch (Exception e) {
            response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
            return null;
        }
        return AccountMapper.INSTANCE.map(account);
    }
}
