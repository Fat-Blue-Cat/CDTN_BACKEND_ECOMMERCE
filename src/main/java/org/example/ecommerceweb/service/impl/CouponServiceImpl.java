package org.example.ecommerceweb.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Coupons;
import org.example.ecommerceweb.exceptions.CouponException;
import org.example.ecommerceweb.repository.CouponRepository;
import org.example.ecommerceweb.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    @Override
    public void createCoupon(Coupons coupons) {
        Optional<Coupons> coupon = couponRepository.findByCode(coupons.getCode());
        if (coupon.isPresent()) {
            throw new RuntimeException("Coupon code already exists");
        }
        coupons.setCreatedAt(java.time.LocalDateTime.now());
        couponRepository.save(coupons);
    }

    @Override
    @Transactional
    public void updateCoupon(Long couponId, Coupons coupons) {

        Coupons couponCurrent = couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("Coupon not found"));
        if(!coupons.getCode().equals(couponCurrent.getCode())){
            Optional<Coupons> coupon = couponRepository.findByCode(coupons.getCode());
            if (coupon.isPresent()) {
                throw new RuntimeException("Coupon code already exists");
            }
        }
        couponCurrent.setCode(coupons.getCode());
        couponCurrent.setTimesUsed(coupons.getTimesUsed());
        couponCurrent.setCouponEndDate(coupons.getCouponEndDate());
        couponCurrent.setMaxTimesUse(coupons.getMaxTimesUse());
        couponCurrent.setDiscountDescription(coupons.getDiscountDescription());
        couponCurrent.setDiscountValue(coupons.getDiscountValue());
        couponCurrent.setCouponStartDate(coupons.getCouponStartDate());

        couponRepository.save(couponCurrent);
    }

    @Override
    public Coupons getCoupon(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    @Override
    public List<Coupons> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public void deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
    }

    @Override
    public Coupons checkCouponCode(String checkCode) throws CouponException {
        Optional<Coupons> coupon = Optional.ofNullable(couponRepository.findByCode(checkCode).orElseThrow(() -> new RuntimeException("Coupon not found")));
        if (coupon.get().getCouponStartDate().isAfter(java.time.LocalDateTime.now())) {
            throw new CouponException("Coupon not started yet");
        }
        if (coupon.get().getCouponEndDate().isBefore(java.time.LocalDateTime.now())) {
            throw new CouponException("Coupon expired");
        }
        if (coupon.get().getMaxTimesUse() <= coupon.get().getTimesUsed()) {
            throw new CouponException("Coupon expired");
        }

        return coupon.get();

    }
}
