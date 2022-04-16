package com.amazon.service;

import com.amazon.dto.AddressChangeDto;
import com.amazon.entity.Account;
import com.amazon.entity.Address;
import com.amazon.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address create(AddressChangeDto addressChangeDto, Account account) {
        Address address = new Address(account, addressChangeDto.getContent(), true);
        return addressRepository.save(address);
    }

    public Address update(int addressId, AddressChangeDto addressChangeDto, Account account) {
        Address address = checkAddress(addressId, account);
        if (Objects.nonNull(addressChangeDto.getContent())) address.setContent(addressChangeDto.getContent());
        if (Objects.nonNull(addressChangeDto.getEnabled())) address.setEnabled(addressChangeDto.getEnabled());
        return addressRepository.save(address);
    }

    public List<Address> getAll(Account account) {
        return addressRepository.findAllByAccount(account);
    }

    public void delete(int addressId, Account account) {
        addressRepository.delete(checkAddress(addressId, account));
    }

    public Address checkAddress(int addressId, Account account) {
        Address address = addressRepository.findByAccountAndId(account, addressId);
        if (Objects.isNull(address)) {
            throw new IllegalArgumentException("There's no such address for this customer.");
        }
        return address;
    }
}
