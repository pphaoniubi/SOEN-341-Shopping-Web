package com.amazon.registration;

import com.amazon.entity.Account;
import com.amazon.entity.CurrentUser;
import com.amazon.entity.Customer;
import com.amazon.entity.Role;
import com.amazon.registration.RegistrationRequest;
import com.amazon.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;

@Service
public class RegistrationService {
    private final AccountService accountService;
    private final EmailValidator emailValidator;

    public RegistrationService(AccountService accountService, EmailValidator emailValidator){
        this.accountService = accountService;
        this.emailValidator = emailValidator;
    }
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return accountService.signUpAccount(new Account(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword()

        )
        );


    }
}
