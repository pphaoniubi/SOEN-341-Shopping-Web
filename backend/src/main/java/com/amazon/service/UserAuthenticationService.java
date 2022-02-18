package com.amazon.service;

import com.amazon.entity.Account;
import com.amazon.entity.CurrentUser;
import com.amazon.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email.toLowerCase());
        if (account != null) {
            RequestContextHolder.getRequestAttributes().setAttribute("loginAccount", account, RequestAttributes.SCOPE_REQUEST);

            List<GrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(account.getRole().getName()));
            return new CurrentUser(account, authorityList);
        }
        throw new UsernameNotFoundException("Account not found.");
    }
}
