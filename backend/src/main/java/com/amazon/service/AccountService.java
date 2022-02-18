package com.amazon.service;

import com.amazon.entity.Account;
import com.amazon.repository.AccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account login(String email, String password, HttpServletRequest request) throws ServletException {
        SecurityContextHolder.clearContext();
        //trigger spring security authentication.
        request.login(email, password);
        RequestContextHolder.getRequestAttributes().getSessionId();
        return (Account) RequestContextHolder.getRequestAttributes().getAttribute("loginAccount", RequestAttributes.SCOPE_REQUEST);
    }

    public Optional<Account> findByAccountId(int accountId) {
        return accountRepository.findById(accountId);
    }
}
