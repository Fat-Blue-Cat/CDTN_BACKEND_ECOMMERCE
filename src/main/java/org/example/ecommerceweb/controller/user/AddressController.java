package org.example.ecommerceweb.controller.user;


import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Address;
import org.example.ecommerceweb.service.AddressService;
import org.example.ecommerceweb.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor
public class AddressController {
    private final AuthService authService;
    private final AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestBody Address address, @RequestHeader(value = "Authorization") String jwt) {
        try {
            address.setUser(authService.findUserByJwt(jwt));
            addressService.createAddress(address);
            return ResponseEntity.ok("Address created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable Long addressId, @RequestBody Address address) {
        try {
            addressService.updateAddress(addressId,address);
            return ResponseEntity.ok("Address updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok("Address deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddress(@PathVariable Long addressId) {
        try {
            return ResponseEntity.ok(addressService.getAddress(addressId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAddresses(@RequestHeader(value = "Authorization") String jwt) {
        try {
            return ResponseEntity.ok(addressService.getAddressByUserId(authService.findUserByJwt(jwt).getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
