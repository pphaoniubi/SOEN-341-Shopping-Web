package com.amazon.repository;

import com.amazon.entity.Account;
import com.amazon.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address findByAccountAndId(Account account, int addressId);
    List<Address> findAllByAccount(Account account);
}
