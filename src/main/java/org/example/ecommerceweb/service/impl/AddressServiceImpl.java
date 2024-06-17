package org.example.ecommerceweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Address;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.repository.AddressRepository;
import org.example.ecommerceweb.repository.UserRepository;
import org.example.ecommerceweb.service.AddressService;
import org.example.ecommerceweb.util.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void updateAddress(Long addressId,Address address) {
        Address addressCurrent = addressRepository.findById(addressId).get();
        addressCurrent.setAddress(address.getAddress());
        addressCurrent.setEmail(address.getEmail());
        addressCurrent.setFullName(address.getFullName());
        addressCurrent.setMobileNumber(address.getMobileNumber());

        addressRepository.save(addressCurrent);

    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public Address getAddress(Long addressId) {
        return addressRepository.findById(addressId).get();
    }

    @Override
    public List<Address> getAddressByUserId(Long userId) {
        return addressRepository.findAllByUserId(userId);
    }
}
