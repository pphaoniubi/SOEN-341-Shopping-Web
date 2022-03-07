package com.amazon.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

import static com.amazon.constant.Constant.PASSWORD_LENGTH_MAX;
import static com.amazon.constant.Constant.PASSWORD_LENGTH_MIN;

public class RegisterDto {

    private final String firstName;
    private final String lastName;

    @Email(message = "Email is not valid")
    private final String email;

    @Length(min = PASSWORD_LENGTH_MIN, max = PASSWORD_LENGTH_MAX,
            message = "Password size must be between " + PASSWORD_LENGTH_MIN + " and " + PASSWORD_LENGTH_MAX + ".")
    private final String password;

    public RegisterDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
