package com.amazon.service;

import com.amazon.entity.Account;
import com.amazon.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public AccountService(AccountRepository accountRepository1, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder1){
        this.accountRepository = accountRepository1;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
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

    public String signUpAccount(Account account) {
       boolean accountExists = accountRepository
                .findByEmail(account.getEmail())
                .isPresent();  //undefined function

        if(accountExists){
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(account.getPassword());

        account.setPassword(encodedPassword);

        accountRepository.save(account);
        // TODO: Send confirmation token

        return "it works";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
