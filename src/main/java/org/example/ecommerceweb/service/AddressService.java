package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Address;

import java.util.List;
import java.util.Objects;

public interface AddressService {

    Address createAddress(Address address);
    void updateAddress(Long addressId,Address address);
    void deleteAddress(Long addressId);
    Address getAddress(Long addressId);
    List<Address> getAddressByUserId(Long userId);

}
