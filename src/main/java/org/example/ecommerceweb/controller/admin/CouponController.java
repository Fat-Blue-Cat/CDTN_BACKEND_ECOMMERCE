package org.example.ecommerceweb.controller.admin;


import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Coupons;
import org.example.ecommerceweb.domains.Permission;
import org.example.ecommerceweb.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PostMapping("/create")
    public ResponseEntity<?> createCoupon(@RequestBody Coupons coupons) {
        try {
           couponService.createCoupon(coupons);
            return ResponseEntity.ok("coupon created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{couponId}")
    public ResponseEntity<?> updateCoupon(@PathVariable Long couponId, @RequestBody Coupons coupons) {
        try {
            couponService.updateCoupon(couponId, coupons);
            return ResponseEntity.ok("coupon updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{couponId}")
    public ResponseEntity<?> getCoupon(@PathVariable Long couponId) {
        try {
            return ResponseEntity.ok(couponService.getCoupon(couponId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCoupons() {
        try {
            return ResponseEntity.ok(couponService.getAllCoupons());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long couponId) {
        try {
            couponService.deleteCoupon(couponId);
            return ResponseEntity.ok("coupon deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
