package com.amazon.service;

import com.amazon.entity.Account;
import com.amazon.entity.Role;
import com.amazon.dto.RegisterDto;
import com.amazon.repository.AccountRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountService(AccountRepository accountRepository1, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.accountRepository = accountRepository1;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Account login(String email, String password, HttpServletRequest request) throws ServletException {
        SecurityContextHolder.clearContext();
        //trigger spring security authentication.
        request.login(email, password);
        RequestContextHolder.getRequestAttributes().getSessionId();
        return (Account) RequestContextHolder.getRequestAttributes().getAttribute("loginAccount", RequestAttributes.SCOPE_REQUEST);
    }

    public Optional<Account> findByAccountId(long accountId) {
        return accountRepository.findById(accountId);
    }

    public Account register(RegisterDto request) {
        if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new IllegalArgumentException("Email is not valid.");
        }
        Account account = accountRepository.findByEmail(request.getEmail());
        if (Objects.nonNull(account)) {
            throw new IllegalArgumentException("Email is already used.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        account = new Account(request.getFirstName(), request.getLastName(), request.getEmail(), encodedPassword, new Role(1, "CUSTOMER"));

        accountRepository.save(account);
        return account;
    }
}
