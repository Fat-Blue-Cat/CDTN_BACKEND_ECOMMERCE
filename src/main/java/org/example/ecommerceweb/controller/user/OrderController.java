package org.example.ecommerceweb.controller.user;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Address;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.repository.UserRepository;
import org.example.ecommerceweb.service.AddressService;
import org.example.ecommerceweb.service.AuthService;
import org.example.ecommerceweb.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final AuthService authService;
    private final AddressService addressService;

    @PutMapping("/create/{addressId}")
    public ResponseEntity<?> createOrder(@RequestHeader(value = "Authorization") String jwt, @PathVariable Long addressId) {
        try{
            User user = authService.findUserByJwt(jwt);
            Address address =addressService.getAddress(addressId);
            return new ResponseEntity<>(orderService.createOrder(user,address), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getOrderHistory(@PathVariable Long userId) {
        try{
            return new ResponseEntity<>(orderService.usersOrderHistory(userId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try{
            return new ResponseEntity<>(orderService.canceledOrder(orderId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
