package org.example.ecommerceweb.controller.user;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/coupons")
@RequiredArgsConstructor
public class CouponUserController {
    private final CouponService couponService;

    @GetMapping("/{checkCode}")
    public ResponseEntity<?> checkCouponCode(@PathVariable String checkCode){
        try{
            return new ResponseEntity<>(couponService.checkCouponCode(checkCode), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
