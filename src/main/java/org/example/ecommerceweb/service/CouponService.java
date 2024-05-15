package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Coupons;
import org.example.ecommerceweb.exceptions.CouponException;

import java.util.List;

public interface CouponService {
    void createCoupon(Coupons coupons);
    void updateCoupon(Long couponId, Coupons coupons);
    Coupons getCoupon(Long couponId);
    List<Coupons> getAllCoupons();
    void deleteCoupon(Long couponId);
    Coupons checkCouponCode(String checkCode) throws CouponException;
}
