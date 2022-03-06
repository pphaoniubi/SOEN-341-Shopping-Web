package com.amazon.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class CurrentUser extends User implements Serializable {

    private final long accountId;

    public CurrentUser(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getEmail(), account.getPassword(), authorities);
        this.accountId = account.getId();
    }

    public long getAccountId(){
        return accountId;
    }

    @Override
    public String toString(){
       return "Account Id: " + accountId + " " + super.toString();
    }
}
