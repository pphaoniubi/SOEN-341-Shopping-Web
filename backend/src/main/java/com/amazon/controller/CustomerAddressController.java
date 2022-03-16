package com.amazon.controller;

import com.amazon.dto.AddressChangeDto;
import com.amazon.dto.AddressDto;
import com.amazon.entity.Account;
import com.amazon.mapper.AddressMapper;
import com.amazon.service.AccountService;
import com.amazon.service.AddressService;
import com.amazon.util.Util;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer/address")
public class CustomerAddressController {

    private final AccountService accountService;
    private final AddressService addressService;

    public CustomerAddressController(AccountService accountService, AddressService addressService) {
        this.accountService = accountService;
        this.addressService = addressService;
    }

    @PostMapping
    @Transactional
    public AddressDto createAddress(@RequestBody AddressChangeDto addressChangeDto) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        return AddressMapper.INSTANCE.map(addressService.create(addressChangeDto, account));
    }

    @PutMapping("/{addressId}")
    @Transactional
    public AddressDto updateAddress(@PathVariable int addressId,
                                          @RequestBody AddressChangeDto addressChangeDto) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        return AddressMapper.INSTANCE.map(addressService.update(addressId, addressChangeDto, account));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<AddressDto> getAllAddresss() {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        return AddressMapper.INSTANCE.map(addressService.getAll(account));
    }

    @DeleteMapping("/{addressId}")
    @Transactional
    public void deleteAddress(@PathVariable int addressId) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        addressService.delete(addressId, account);
    }
    
}
