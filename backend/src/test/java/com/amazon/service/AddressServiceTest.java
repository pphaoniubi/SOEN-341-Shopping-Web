package com.amazon.service;

import com.amazon.dto.AddressChangeDto;
import com.amazon.entity.Account;
import com.amazon.entity.Address;
import com.amazon.entity.Role;
import com.amazon.repository.AddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@DisplayName("A test case for class AddressService.")
@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;
    private AddressService addressService;
    private Account account;
    private AddressChangeDto addressChangeDto;
    private Address address;

    @BeforeEach
    void setup() {
        addressService = new AddressService(addressRepository);
        account = new Account("AAA", "BBB", "aaabbb@gmail.com", "aaabbb123", new Role(1, "CUSTOMER"));
        addressChangeDto = new AddressChangeDto("content", true);
        address = new Address(account, addressChangeDto.getContent(), true);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("test create method.")
    @Test
    public void testCreate() {
        Address address = new Address(account, addressChangeDto.getContent(), true);
        when(addressRepository.save(address)).thenReturn(address);
        Address actual = addressService.create(addressChangeDto, account);
        assertThat(actual.getContent()).isEqualTo(address.getContent());
    }

    @DisplayName("test update method.")
    @Test
    public void testUpdate() {
        int addressId = 1;
        address.setId(addressId);
        when(addressRepository.findByAccountAndId(account, addressId)).thenReturn(address);
        when(addressService.checkAddress(addressId, account)).thenReturn(address);
        addressService.update(addressId, addressChangeDto, account);
        verify(addressRepository).save(address);
    }

    @DisplayName("test getAll method.")
    @Test
    public void testGetAll() {
        addressService.getAll(account);
        verify(addressRepository).findAllByAccount(account);
    }

    @DisplayName("test delete method.")
    @Test
    public void testDelete() {
        int addressId = 1;
        address.setId(addressId);
        when(addressRepository.findByAccountAndId(account, addressId)).thenReturn(address);
        when(addressService.checkAddress(addressId, account)).thenReturn(address);
        addressService.delete(addressId, account);
        verify(addressRepository).delete(address);
    }
}
