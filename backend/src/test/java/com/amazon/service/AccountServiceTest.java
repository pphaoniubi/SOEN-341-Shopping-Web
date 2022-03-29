package com.amazon.service;

import com.amazon.dto.RegisterDto;
import com.amazon.entity.Account;
import com.amazon.entity.Role;
import com.amazon.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("A test case for class AccountService.")
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    private static final int ACCOUNT_ID = 1;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Account account;
    private AccountService accountService;
    private RegisterDto registerDto;

    @DisplayName("Set up testing data for AccountService.")
    @BeforeEach
    void setup() {
        account = new Account("AAA", "BBB", "aaabbb@gmail.com", "aaabbb123", new Role(1, "CUSTOMER"));
        accountService = new AccountService(accountRepository, bCryptPasswordEncoder);
        registerDto = new RegisterDto(account.getFirstName(), account.getLastName(), account.getEmail(), account.getPassword());
    }

    @DisplayName("Test findById method.")
    @Test
    public void testFindById() {
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        accountService.findById(ACCOUNT_ID);
        verify(accountRepository).findById(ACCOUNT_ID);
    }
}
