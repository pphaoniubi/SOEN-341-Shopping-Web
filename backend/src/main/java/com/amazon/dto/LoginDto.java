package com.amazon.dto;

//import org.hibernate.validator.constraints.Length;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;

public class LoginDto {

//    @Email(message = "Email is not valid")
//    @Length(max=300)
    private String email;

//    @NotBlank(message = "Password could not be blank.")
//    @Length(min = 6, max = 30, message = "Password's length should be between 6 and 30.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
